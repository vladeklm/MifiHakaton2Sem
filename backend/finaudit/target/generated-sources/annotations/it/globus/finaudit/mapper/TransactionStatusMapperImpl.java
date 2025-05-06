package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.TransactionStatusDTO;
import it.globus.finaudit.entity.TransactionStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T16:21:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TransactionStatusMapperImpl implements TransactionStatusMapper {

    @Override
    public TransactionStatus toEntity(TransactionStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionStatus transactionStatus = new TransactionStatus();

        transactionStatus.setCode( dto.getCode() );
        transactionStatus.setName( dto.getName() );

        return transactionStatus;
    }

    @Override
    public TransactionStatusDTO toDTO(TransactionStatus entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionStatusDTO transactionStatusDTO = new TransactionStatusDTO();

        transactionStatusDTO.setId( entity.getId() );
        transactionStatusDTO.setCode( entity.getCode() );
        transactionStatusDTO.setName( entity.getName() );

        return transactionStatusDTO;
    }

    @Override
    public void updateEntity(TransactionStatusDTO dto, TransactionStatus entity) {
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
