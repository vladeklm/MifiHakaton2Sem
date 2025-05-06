package it.globus.finaudit.controller.DictionaryControllers;

import it.globus.finaudit.DTO.TransactionTypeDTO;
import it.globus.finaudit.service.operations.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-types")
@RequiredArgsConstructor
public class TransactionTypeController {
    private final TransactionTypeService service;

    @GetMapping
    public List<TransactionTypeDTO> getAll() {
        return service.getAll();
    }
}