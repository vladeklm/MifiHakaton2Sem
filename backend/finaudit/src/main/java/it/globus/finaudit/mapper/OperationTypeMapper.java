package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.OperationTypeDTO;
import it.globus.finaudit.entity.OperationType;

@Mapper(componentModel = "spring")
public interface OperationTypeMapper {

    @Mapping(target = "id", ignore = true)
    OperationType toEntity(OperationTypeDTO dto);

    OperationTypeDTO toDTO(OperationType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(OperationTypeDTO dto, @MappingTarget OperationType entity);
}
