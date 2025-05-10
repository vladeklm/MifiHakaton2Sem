package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    Category toEntity(CategoryDTO dto);

    CategoryDTO toDTO(Category entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(CategoryDTO dto, @MappingTarget Category entity);
}
