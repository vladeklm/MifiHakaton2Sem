package it.globus.finaudit.mapper;


import it.globus.finaudit.DTO.RegisterDTO;
import it.globus.finaudit.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "secondName", source = "secondName")
    @Mapping(target = "patronymic", source = "patronymic")
    @Mapping(target = "bankAccounts", ignore = true)
    @Mapping(target = "operations", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Client toClient(RegisterDTO dto);
}
