package it.globus.finaudit.controller.operations;


import it.globus.finaudit.DTO.OperationStatusDTO;

import it.globus.finaudit.service.operations.OperationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/transaction-statuses")
@RequiredArgsConstructor
public class OperationStatusController {
    private final OperationStatusService service;

    @GetMapping
    public List<OperationStatusDTO> getAll() {
        return service.getAll();
    }
}