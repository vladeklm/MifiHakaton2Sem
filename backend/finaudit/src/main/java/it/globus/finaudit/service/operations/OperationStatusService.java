package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.OperationStatusDTO;
import it.globus.finaudit.mapper.OperationStatusMapper;
import it.globus.finaudit.repository.OperationStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationStatusService {
    private final OperationStatusRepository repository;
    private final OperationStatusMapper mapper;

    public List<OperationStatusDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
