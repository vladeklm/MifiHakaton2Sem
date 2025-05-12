package it.globus.finaudit.mapper;

import it.globus.finaudit.entity.OperationCategory;
import org.mapstruct.*;
import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.entity.Category;

@Mapper(componentModel = "spring")
public interface OperationCategoryMapper {

  @Mapping(target = "id", ignore = true)
  OperationCategory toEntity(CategoryDTO dto);

  CategoryDTO toDTO(OperationCategory entity);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntity(CategoryDTO dto, @MappingTarget OperationCategory entity);
}
