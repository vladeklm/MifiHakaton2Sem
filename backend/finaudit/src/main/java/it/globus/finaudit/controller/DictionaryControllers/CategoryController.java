package it.globus.finaudit.controller.DictionaryControllers;

import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.service.operations.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @GetMapping
    public List<CategoryDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/by-type/{type}")
    public List<CategoryDTO> getByType(@PathVariable String type) {
        return service.getByType(type);
    }
}