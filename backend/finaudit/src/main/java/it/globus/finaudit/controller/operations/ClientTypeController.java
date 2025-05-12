package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.ClientTypeDTO;
import it.globus.finaudit.mapper.ClientTypeMapper;
import it.globus.finaudit.repository.ClientTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/operations/Client-types")
@RequiredArgsConstructor
public class ClientTypeController {
    private final ClientTypeRepository repository;
    private final ClientTypeMapper mapper;

    @GetMapping
    public List<ClientTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }
}
