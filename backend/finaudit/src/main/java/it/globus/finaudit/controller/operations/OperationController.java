package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.OperationDto;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.service.operations.OperationService;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("api/v1/operations")
@RequiredArgsConstructor
public class OperationController {

    private final OperationService service;

    @PostMapping
    public ResponseEntity<String> createOperation(@RequestBody Operation operation) {
        return service.createOperation(operation);
    }

    @GetMapping
    public Page<OperationDto> getOperationList(
            @RequestParam Long clientId,
            Pageable pageable
    ) {
        return service.getOperationList(clientId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperationDto> getOperation(@PathVariable Long id) {
        return service.getOperation(id);
    }

    @PutMapping("/change_data/{id}")
    public ResponseEntity<String> updateDataOperation(@RequestBody OperationDto dto, @PathVariable Long id) {
        return service.updateDataOperation(dto, id);
    }

}
