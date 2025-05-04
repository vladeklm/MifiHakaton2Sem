package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.reportfilter.PersonTypeDTO;
import it.globus.finaudit.mapper.DictionaryMapper;
import it.globus.finaudit.repository.PersonTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/person-types")
@RequiredArgsConstructor
public class PersonTypeController {
    private final PersonTypeRepository repository;
    private final DictionaryMapper mapper;

    @GetMapping
    public List<PersonTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toPersonTypeDTO)
                .toList();
    }
}