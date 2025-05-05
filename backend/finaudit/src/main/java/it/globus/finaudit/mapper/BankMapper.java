package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.entity.Bank;

@Mapper(componentModel = "spring")
public interface BankMapper {

    @Mapping(target = "id", ignore = true) // Игнорируем ID при создании
    Bank toEntity(BankDTO dto);

    BankDTO toDTO(Bank entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(BankDTO dto, @MappingTarget Bank entity);
}
