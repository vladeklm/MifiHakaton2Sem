package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.AccountInfoDTO;
import it.globus.finaudit.repository.OperationRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountService {

    private final OperationRepository operationRepository;

    public AccountService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public List<AccountInfoDTO> getAccountsByClient(Long clientId, boolean isContragentAccount) {
        return operationRepository.findAccountsByClientAndType(clientId, isContragentAccount);
    }
}