package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.OperationTypeDTO;
import it.globus.finaudit.mapper.OperationTypeMapper;
import it.globus.finaudit.repository.OperationTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationTypeService {
    private final OperationTypeRepository repository;
    private final OperationTypeMapper mapper;

    public List<OperationTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
