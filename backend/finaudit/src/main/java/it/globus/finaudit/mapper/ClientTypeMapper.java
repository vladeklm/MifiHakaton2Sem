package it.globus.finaudit.mapper;

import it.globus.finaudit.entity.ClientType;
import org.mapstruct.*;
import it.globus.finaudit.DTO.ClientTypeDTO;

@Mapper(componentModel = "spring")
public interface ClientTypeMapper {

    @Mapping(target = "id", ignore = true)
    ClientType toEntity(ClientTypeDTO dto);

    ClientTypeDTO toDTO(ClientType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ClientTypeDTO dto, @MappingTarget ClientType entity);
}
