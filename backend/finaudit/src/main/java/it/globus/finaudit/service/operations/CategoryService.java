package it.globus.finaudit.service.operations;

import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.entity.Category;
import it.globus.finaudit.mapper.CategoryMapper;
import it.globus.finaudit.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<CategoryDTO> getByType(String type) {
        return repository.findByType(type).stream()
                .map(mapper::toDTO)
                .toList();
    }
}
