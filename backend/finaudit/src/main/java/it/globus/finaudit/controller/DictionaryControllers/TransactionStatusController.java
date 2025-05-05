package it.globus.finaudit.controller.DictionaryControllers;

import it.globus.finaudit.DTO.TransactionStatusDTO;
import it.globus.finaudit.service.operations.TransactionStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaction-statuses")
@RequiredArgsConstructor
public class TransactionStatusController {
    private final TransactionStatusService service;

    @GetMapping
    public List<TransactionStatusDTO> getAll() {
        return service.getAll();
    }
}