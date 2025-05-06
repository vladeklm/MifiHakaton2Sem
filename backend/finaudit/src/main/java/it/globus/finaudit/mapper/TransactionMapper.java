package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.TransactionDTO;
import it.globus.finaudit.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toEntity(TransactionDTO dto);
    TransactionDTO toDTO(Transaction entity);
}
