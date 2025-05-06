package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.OperationStatusDTO;
import it.globus.finaudit.entity.OperationStatus;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OperationStatusMapper {

    @Mapping(target = "id", ignore = true)
    OperationStatus toEntity(OperationStatusDTO dto);

    OperationStatusDTO toDTO(OperationStatus entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(OperationStatusDTO dto, @MappingTarget OperationStatus entity);
}
