package it.globus.finaudit.controller.DictionaryControllers;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.service.operations.BankService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService service;

    @GetMapping
    public List<BankDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BankDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public BankDTO create(@Valid @RequestBody BankDTO dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public BankDTO update(@PathVariable Long id, @Valid @RequestBody BankDTO dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
