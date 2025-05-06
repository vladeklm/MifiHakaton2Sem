package it.globus.finaudit.util.mapper;


import it.globus.finaudit.DTO.AuthenticationDTO;
import it.globus.finaudit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toUser(AuthenticationDTO dto);
}
