package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.TransactionStatusDTO;
import it.globus.finaudit.entity.TransactionStatus;
import it.globus.finaudit.mapper.TransactionStatusMapper;
import it.globus.finaudit.repository.TransactionStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionStatusService {
    private final TransactionStatusRepository repository;
    private final TransactionStatusMapper mapper;

    public List<TransactionStatusDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
