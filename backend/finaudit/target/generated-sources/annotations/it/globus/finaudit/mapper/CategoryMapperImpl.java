package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.entity.Category;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T00:16:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public Category toEntity(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setName( dto.getName() );
        category.setType( dto.getType() );

        return category;
    }

    @Override
    public CategoryDTO toDTO(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setName( entity.getName() );
        categoryDTO.setType( entity.getType() );

        return categoryDTO;
    }

    @Override
    public void updateEntity(CategoryDTO dto, Category entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getType() != null ) {
            entity.setType( dto.getType() );
        }
    }
}
