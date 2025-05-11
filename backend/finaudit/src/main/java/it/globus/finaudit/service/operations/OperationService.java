package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.OperationDTO;
import it.globus.finaudit.mapper.OperationMapper;
import it.globus.finaudit.entity.*;
import it.globus.finaudit.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository repository;
    private final OperationMapper mapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final OperationCategoryRepository operationCategoryRepository;
    private final OperationTypeRepository operationTypeRepository;
    private final ClientTypeRepository clientTypeRepository;
    private final OperationStatusRepository operationStatusRepository;
    private final BankAccountRepository bankAccountRepository;
    private final BankRepository bankRepository;

    public ResponseEntity<?> createOperation(OperationDTO dto) {
        try {
            Operation validDataOp = validate(dto);
            saveOperation(validDataOp, dto);
            return ResponseEntity.ok(Map.of("message", "success"));
        } catch (EntityNotFoundException | DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @Transactional
    public void saveOperation(Operation validDataOp, OperationDTO dto) {
        Operation operation = Operation.builder()
                .operationCategory(validDataOp.getOperationCategory())
                .operationType(validDataOp.getOperationType())
                .operationStatus(validDataOp.getOperationStatus())
                .clientType(validDataOp.getClientType())
                .bankAccount(validDataOp.getBankAccount())
                .bankFrom(validDataOp.getBankFrom())
                .bankTo(validDataOp.getBankTo())
                .bankRecipientAccount(validDataOp.getBankRecipientAccount())
                .client(validDataOp.getClient())
                .inn(dto.getInn())
                .phoneNumber(dto.getPhoneNumber())
                .amount(dto.getAmount())
                .comment(dto.getComment())
                .dateTimeOperation(dto.getDateTimeOperation())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        repository.save(operation);
    }

    public Operation validate(OperationDTO dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Client client = clientRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        OperationCategory operationCategory = operationCategoryRepository.findByName(dto.getOperationCategoryName())
                .orElseThrow(() -> new EntityNotFoundException("OperationCategory not found"));

        OperationType operationType = operationTypeRepository.findByName(dto.getOperationTypeName())
                .orElseThrow(() -> new EntityNotFoundException("OperationType not found"));

        OperationStatus operationStatus = operationStatusRepository.findByName(dto.getOperationStatusName())
                .orElseThrow(() -> new EntityNotFoundException("OperationStatus not found"));

        ClientType clientType = clientTypeRepository.findByName(dto.getClientTypeName())
                .orElseThrow(() -> new EntityNotFoundException("ClientType not found"));

        boolean isIncoming = operationType.getName().equalsIgnoreCase("Поступление");

        Bank bankFrom = null;

        if (!isIncoming) {
            bankFrom = bankRepository.findByName(dto.getBankFromName())
                    .orElseThrow(() -> new EntityNotFoundException("Bank From not found"));
        }

        Bank bankTo = bankRepository.findByName(dto.getBankToName())
                .orElseThrow(() -> new EntityNotFoundException("Bank To not found"));

        Bank mainBank = isIncoming ? bankTo : bankFrom;

        BankAccount bankAccount;
        Optional<BankAccount> existingAccountByNumber = bankAccountRepository.findByNumber(dto.getBankAccountNumber().toString());

        if (existingAccountByNumber.isPresent()) {
            BankAccount existing = existingAccountByNumber.get();
            if (!existing.getBank().equals(mainBank)) {
                // Такой номер уже есть, но в другом банке — ошибка
                throw new DataIntegrityViolationException("Bank account number already exists in another bank");
            }
            bankAccount = existing;
        } else {
            // Такого номера нет вообще — можно создать
            bankAccount = bankAccountRepository.save(BankAccount.builder()
                    .bank(mainBank)
                    .client(client)
                    .number(dto.getBankAccountNumber().toString())
                    .build());
        }

        BankAccount bankRecipientAccount = null;
        if (!isIncoming && dto.getBankRecipientAccountNumber() != null) {
            String recipientNumber = String.valueOf(dto.getBankRecipientAccountNumber());

            Optional<BankAccount> existingRecipientAccount = bankAccountRepository.findByNumber(recipientNumber);

            if (existingRecipientAccount.isPresent()) {
                BankAccount existing = existingRecipientAccount.get();
                if (!existing.getBank().equals(bankTo)) {
                    throw new DataIntegrityViolationException("Recipient account number already exists in another bank");
                }
                bankRecipientAccount = existing;
            } else {
                // Счёт ещё не существует — создаём ничейный (без клиента)
                bankRecipientAccount = bankAccountRepository.save(
                        BankAccount.builder()
                                .bank(bankTo)
                                .number(recipientNumber)
                                .client(null) // Ничейный
                                .build()
                );
            }
        }

        return Operation.builder()
                .operationCategory(operationCategory)
                .operationType(operationType)
                .operationStatus(operationStatus)
                .clientType(clientType)
                .bankAccount(bankAccount)
                .bankRecipientAccount(bankRecipientAccount)
                .bankTo(bankTo)
                .bankFrom(bankFrom)
                .client(client)
                .build();
    }

    public Page<OperationDTO> getOperationsByClientId(Long clientId, Pageable pageable) {

        Page<Operation> operationPage = repository.findAllByClient_User_Id(clientId, pageable);

        return operationPage.map(mapper::toDto);
    }

    public ResponseEntity<OperationDTO> getOperationById(Long id) {
        Optional<Operation> operation = repository.findById(id);

        return operation.map(op -> ResponseEntity.ok(mapper.toDto(op)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> updateDataOperation(Operation updatedOp, Long id) {
        return repository.findById(id)
                .map(existingOp -> {
                    updateOperationFields(existingOp, updatedOp);
                    existingOp.setUpdatedAt(LocalDateTime.now());
                    repository.save(existingOp);
                    return ResponseEntity.ok("Операция успешно обновлена");
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private void updateOperationFields(Operation existing, Operation updated) {
        if (updated.getAmount() != null) existing.setAmount(updated.getAmount());
        if (updated.getBankFrom() != null) existing.setBankFrom(updated.getBankFrom());
        if (updated.getBankTo() != null) existing.setBankTo(updated.getBankTo());
        if (updated.getPhoneNumber() != null) existing.setPhoneNumber(updated.getPhoneNumber());
        if (updated.getInn() != null) existing.setInn(updated.getInn());
        if (updated.getComment() != null) existing.setComment(updated.getComment());
        if (updated.getDateTimeOperation() != null) existing.setDateTimeOperation(updated.getDateTimeOperation());
        if (updated.getBankRecipientAccount() != null) existing.setBankRecipientAccount(updated.getBankRecipientAccount());
        if (updated.getOperationCategory() != null) existing.setOperationCategory(updated.getOperationCategory());
        if (updated.getOperationType() != null) existing.setOperationType(updated.getOperationType());
        if (updated.getClientType() != null) existing.setClientType(updated.getClientType());
        if (updated.getOperationStatus() != null) existing.setOperationStatus(updated.getOperationStatus());
    }


}
