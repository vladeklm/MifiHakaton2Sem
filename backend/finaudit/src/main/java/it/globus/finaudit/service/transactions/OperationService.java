package it.globus.finaudit.service.transactions;

import it.globus.finaudit.DTO.OperationDto;
import it.globus.finaudit.DTO.OperationMapper;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.OperationRepository;
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

    public ResponseEntity<String> createOperation(Operation operation) {

        var newOperation = Operation.builder()
                .dateTimeOperation(operation.getDateTimeOperation())
                .operationCategory(operation.getOperationCategory())
                .operationStatus(operation.getOperationStatus())
                .operationType(operation.getOperationType())
                .amount(operation.getAmount())
                .bankAccount(operation.getBankAccount())
                .createdAt(operation.getCreatedAt())
                .bankFromId(operation.getBankFromId())
                .bankToId(operation.getBankToId())
                .bankRecipientAccountId(operation.getBankRecipientAccountId())
                .client(operation.getClient())
                .inn(operation.getInn())
                .comment(operation.getComment())
                .clientType(operation.getClientType())
                .phoneNumber(operation.getPhoneNumber())
                .updatedAt(operation.getUpdatedAt())
                .build();

        if (newOperation != null) {

            repository.save(operation);

            return ResponseEntity.ok("success");
        }

        return ResponseEntity.ofNullable("bad request");
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

        // Обновляем вложенные сущности, если они существуют
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
