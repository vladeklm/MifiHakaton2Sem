package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.OperationDTO;
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
    public ResponseEntity<?> createOperation(@RequestBody OperationDTO dto) {
        return service.createOperation(dto);
    }

    @GetMapping
    public Page<OperationDTO> getOperationsByClientId(
            @RequestParam Long clientId,
            Pageable pageable
    ) {
        return service.getOperationsByClientId(clientId, pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOperationById(@PathVariable Long id) {
        return service.getOperationById(id);
    }

    @PutMapping("/change_data/{id}")
    public ResponseEntity<String> updateDataOperation(@RequestBody Operation operation, @PathVariable Long id) {
        return service.updateDataOperation(operation, id);
    }

}
