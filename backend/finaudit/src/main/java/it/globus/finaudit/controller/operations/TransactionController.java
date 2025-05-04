package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.TransactionDTO;
import it.globus.finaudit.service.operations.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService service;

    @GetMapping
    public List<TransactionDTO> getFilteredTransactions(
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return service.getFilteredTransactions(type, status, startDate, endDate);
    }

    @PostMapping
    public TransactionDTO createTransaction(@RequestBody TransactionDTO dto) {
        return service.createTransaction(dto);
    }
}
