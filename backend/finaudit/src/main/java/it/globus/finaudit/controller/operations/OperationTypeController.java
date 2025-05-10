package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.OperationTypeDTO;
import it.globus.finaudit.service.operations.OperationTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api//v1/transaction-types")
@RequiredArgsConstructor
public class OperationTypeController {
    private final OperationTypeService service;

    @GetMapping
    public List<OperationTypeDTO> getAll() {
        return service.getAll();
    }
}