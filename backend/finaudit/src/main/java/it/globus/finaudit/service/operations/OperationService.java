package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.OperationDto;
import it.globus.finaudit.DTO.OperationMapper;
import it.globus.finaudit.entity.*;
import it.globus.finaudit.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Transactional
    public ResponseEntity<String> createOperation(Operation operation) {

        User user = userRepository.findById(operation.getClient().getUser().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Client client = clientRepository.findByUser(user)
                .orElseThrow(() -> new EntityNotFoundException("Client not found"));

        OperationCategory operationCategory = operationCategoryRepository.findById(operation.getOperationCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("OperationCategory not found"));

        OperationType operationType = operationTypeRepository.findById(operation.getOperationType().getId())
                .orElseThrow(() -> new EntityNotFoundException("OperationType not found"));

        ClientType clientType = clientTypeRepository.findById(operation.getClientType().getId())
                .orElseThrow(() -> new EntityNotFoundException("ClientType not found"));

        OperationStatus operationStatus = operationStatusRepository.findById(operation.getOperationStatus().getId())
                .orElseThrow(() -> new EntityNotFoundException("OperationStatus not found"));

        BankAccount bankAccount = bankAccountRepository.findById(operation.getBankAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("BankAccount not found"));

        var newOperation = Operation.builder()
                .dateTimeOperation(operation.getDateTimeOperation())
                .operationCategory(operationCategory)
                .operationStatus(operationStatus)
                .operationType(operationType)
                .amount(operation.getAmount())
                .bankAccount(bankAccount)
                .createdAt(operation.getCreatedAt())
                .bankFromId(operation.getBankFromId())
                .bankToId(operation.getBankToId())
                .bankRecipientAccountId(operation.getBankRecipientAccountId())
                .client(client)
                .inn(operation.getInn())
                .comment(operation.getComment())
                .clientType(clientType)
                .phoneNumber(operation.getPhoneNumber())
                .updatedAt(operation.getUpdatedAt())
                .build();

        repository.save(newOperation);

        return ResponseEntity.ok("success");
    }

    public Page<OperationDto> getOperationList(Long clientId, Pageable pageable) {

        Page<Operation> operationPage = repository.findAllByClient_User_Id(clientId, pageable);

        return operationPage.map(mapper::toDto);
    }

    public ResponseEntity<OperationDto> getOperation(Long id) {
        Optional<Operation> operation = repository.findById(id);
        return operation.map(op -> ResponseEntity.ok(mapper.toDto(op)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<String> updateDataOperation(OperationDto dto, Long id) {

        Optional<Operation> optionalOperation = repository.findById(id);
        if (optionalOperation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Operation operation = optionalOperation.get();

        if (dto.getAmount() != null) operation.setAmount(dto.getAmount());
        if (dto.getBankFromId() != null) operation.setBankFromId(dto.getBankFromId());
        if (dto.getBankToId() != null) operation.setBankToId(dto.getBankToId());
        if (dto.getPhoneNumber() != null) operation.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getInn() != null) operation.setInn(dto.getInn());
        if (dto.getComment() != null) operation.setComment(dto.getComment());
        if (dto.getDateTimeOperation() != null) operation.setDateTimeOperation(dto.getDateTimeOperation());
        if (dto.getBankRecipientAccountId() != null) operation.setBankRecipientAccountId(dto.getBankRecipientAccountId());

        if (dto.getOperationCategoryName() != null && operation.getOperationCategory() != null) {
            operation.getOperationCategory().setName(dto.getOperationCategoryName());
        }
        if (dto.getOperationTypeName() != null && operation.getOperationType() != null) {
            operation.getOperationType().setName(dto.getOperationTypeName());
        }
        if (dto.getClientTypeName() != null && operation.getClientType() != null) {
            operation.getClientType().setName(dto.getClientTypeName());
        }
        if (dto.getOperationStatusName() != null && operation.getOperationStatus() != null) {
            operation.getOperationStatus().setName(dto.getOperationStatusName());
        }

        repository.save(operation);

        return ResponseEntity.ok("Операция успешно обновлена");
    }

}
