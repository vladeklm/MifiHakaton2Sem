package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.service.operations.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }

    /*@GetMapping("/by-type/{type}")
    public List<CategoryDTO> getByType(@PathVariable String type) {
        return service.getByType(type);
    }*/
}
