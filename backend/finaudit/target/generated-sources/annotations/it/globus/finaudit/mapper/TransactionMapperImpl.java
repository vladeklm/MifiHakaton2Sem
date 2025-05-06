package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.TransactionDTO;
import it.globus.finaudit.entity.Transaction;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T16:21:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class TransactionMapperImpl implements TransactionMapper {

    @Override
    public Transaction toEntity(TransactionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Transaction transaction = new Transaction();

        transaction.setId( dto.getId() );
        transaction.setType( dto.getType() );
        transaction.setAmount( dto.getAmount() );
        transaction.setDate( dto.getDate() );
        transaction.setStatus( dto.getStatus() );
        transaction.setSenderBank( dto.getSenderBank() );
        transaction.setReceiverBank( dto.getReceiverBank() );
        transaction.setInn( dto.getInn() );
        transaction.setAccountNumber( dto.getAccountNumber() );
        transaction.setPhone( dto.getPhone() );
        transaction.setComment( dto.getComment() );

        return transaction;
    }

    @Override
    public TransactionDTO toDTO(Transaction entity) {
        if ( entity == null ) {
            return null;
        }

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setId( entity.getId() );
        transactionDTO.setType( entity.getType() );
        transactionDTO.setAmount( entity.getAmount() );
        transactionDTO.setDate( entity.getDate() );
        transactionDTO.setStatus( entity.getStatus() );
        transactionDTO.setSenderBank( entity.getSenderBank() );
        transactionDTO.setReceiverBank( entity.getReceiverBank() );
        transactionDTO.setInn( entity.getInn() );
        transactionDTO.setAccountNumber( entity.getAccountNumber() );
        transactionDTO.setPhone( entity.getPhone() );
        transactionDTO.setComment( entity.getComment() );

        return transactionDTO;
    }
}
