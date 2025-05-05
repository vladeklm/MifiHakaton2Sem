package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.PersonTypeDTO;
import it.globus.finaudit.entity.PersonType;
import it.globus.finaudit.mapper.PersonTypeMapper;
import it.globus.finaudit.repository.PersonTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonTypeService {
    private final PersonTypeRepository repository;
    private final PersonTypeMapper mapper;

    public List<PersonTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
