package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.TransactionStatusDTO;
import it.globus.finaudit.entity.TransactionStatus;

@Mapper(componentModel = "spring")
public interface TransactionStatusMapper {

    @Mapping(target = "id", ignore = true)
    TransactionStatus toEntity(TransactionStatusDTO dto);

    TransactionStatusDTO toDTO(TransactionStatus entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(TransactionStatusDTO dto, @MappingTarget TransactionStatus entity);
}
