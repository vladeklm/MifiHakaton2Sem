package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.ClientTypeDTO;
import it.globus.finaudit.mapper.ClientTypeMapper;
import it.globus.finaudit.repository.ClientTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientTypeService {
    private final ClientTypeRepository repository;
    private final ClientTypeMapper mapper;

    public List<ClientTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
