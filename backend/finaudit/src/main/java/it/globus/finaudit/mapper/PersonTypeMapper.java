package it.globus.finaudit.mapper;

import org.mapstruct.*;
import it.globus.finaudit.DTO.PersonTypeDTO;
import it.globus.finaudit.entity.PersonType;

@Mapper(componentModel = "spring")
public interface PersonTypeMapper {

    @Mapping(target = "id", ignore = true)
    PersonType toEntity(PersonTypeDTO dto);

    PersonTypeDTO toDTO(PersonType entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PersonTypeDTO dto, @MappingTarget PersonType entity);
}
