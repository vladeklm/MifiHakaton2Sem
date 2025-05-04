package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.TransactionTypeDTO;
import it.globus.finaudit.entity.TransactionType;

@Mapper(componentModel = "spring")
public interface TransactionTypeMapper {

    @Mapping(target = "id", ignore = true)
    TransactionType toEntity(TransactionTypeDTO dto);

    TransactionTypeDTO toDTO(TransactionType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TransactionTypeDTO dto, @MappingTarget TransactionType entity);
}
