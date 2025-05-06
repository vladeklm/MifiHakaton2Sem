package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.TransactionTypeDTO;
import it.globus.finaudit.entity.TransactionType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T16:21:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TransactionTypeMapperImpl implements TransactionTypeMapper {

    @Override
    public TransactionType toEntity(TransactionTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionType transactionType = new TransactionType();

        transactionType.setCode( dto.getCode() );
        transactionType.setName( dto.getName() );

        return transactionType;
    }

    @Override
    public TransactionTypeDTO toDTO(TransactionType entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionTypeDTO transactionTypeDTO = new TransactionTypeDTO();

        transactionTypeDTO.setId( entity.getId() );
        transactionTypeDTO.setCode( entity.getCode() );
        transactionTypeDTO.setName( entity.getName() );

        return transactionTypeDTO;
    }

    @Override
    public void updateEntity(TransactionTypeDTO dto, TransactionType entity) {
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
