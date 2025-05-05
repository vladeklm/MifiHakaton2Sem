package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.PersonTypeDTO;
import it.globus.finaudit.entity.PersonType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-05T00:25:37+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class PersonTypeMapperImpl implements PersonTypeMapper {

    @Override
    public PersonType toEntity(PersonTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonType personType = new PersonType();

        personType.setCode( dto.getCode() );
        personType.setName( dto.getName() );

        return personType;
    }

    @Override
    public PersonTypeDTO toDTO(PersonType entity) {
        if ( entity == null ) {
            return null;
        }

        PersonTypeDTO personTypeDTO = new PersonTypeDTO();

        personTypeDTO.setId( entity.getId() );
        personTypeDTO.setCode( entity.getCode() );
        personTypeDTO.setName( entity.getName() );

        return personTypeDTO;
    }

    @Override
    public void updateEntity(PersonTypeDTO dto, PersonType entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getCode() != null ) {
            entity.setCode( dto.getCode() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
    }
}
