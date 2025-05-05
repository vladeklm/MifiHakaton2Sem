package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.TransactionTypeDTO;
import it.globus.finaudit.entity.TransactionType;
import it.globus.finaudit.mapper.TransactionTypeMapper;
import it.globus.finaudit.repository.TransactionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionTypeService {
    private final TransactionTypeRepository repository;
    private final TransactionTypeMapper mapper;

    public List<TransactionTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
