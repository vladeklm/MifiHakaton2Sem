package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.AccountInfoDTO;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.OperationRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class AccountService {

    private final OperationRepository operationRepository;

    // Конструктор с инъекцией зависимости
    public AccountService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Transactional
    public List<AccountInfoDTO> getAccountsByClient(Long clientId, boolean isContragentAccount) {
        List<Operation> operations = operationRepository.findByClientId(clientId);

        return operations.stream()
                .filter(op -> isContragentAccount ?
                        op.getBankRecipientAccount() != null :
                        op.getBankAccount() != null)
                .collect(Collectors.groupingBy(
                        operation -> isContragentAccount ?
                                operation.getBankRecipientAccount() + "|" +
                                        (operation.getRecipientBankName() != null ?
                                                operation.getRecipientBankName() : "Не указан") :
                                operation.getBankAccount() + "|" +
                                        (operation.getBankName() != null ?
                                                operation.getBankName() : "Не указан"),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .map(entry -> {
                    String[] parts = entry.getKey().split("\\|");
                    return new AccountInfoDTO(parts[0], parts[1], entry.getValue());
                })
                .sorted(Comparator.comparingLong(AccountInfoDTO::getUsageCount).reversed())
                .collect(Collectors.toList());
    }
}
