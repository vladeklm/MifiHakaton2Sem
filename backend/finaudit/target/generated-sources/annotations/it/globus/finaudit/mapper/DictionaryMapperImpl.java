package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.DTO.PersonTypeDTO;
import it.globus.finaudit.DTO.TransactionStatusDTO;
import it.globus.finaudit.DTO.TransactionTypeDTO;
import it.globus.finaudit.entity.Bank;
import it.globus.finaudit.entity.Category;
import it.globus.finaudit.entity.PersonType;
import it.globus.finaudit.entity.TransactionStatus;
import it.globus.finaudit.entity.TransactionType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T16:21:05+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class DictionaryMapperImpl implements DictionaryMapper {

    @Override
    public PersonType toPersonType(PersonTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        PersonType personType = new PersonType();

        personType.setId( dto.getId() );
        personType.setCode( dto.getCode() );
        personType.setName( dto.getName() );

        return personType;
    }

    @Override
    public PersonTypeDTO toPersonTypeDTO(PersonType entity) {
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
    public TransactionType toTransactionType(TransactionTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionType transactionType = new TransactionType();

        transactionType.setId( dto.getId() );
        transactionType.setCode( dto.getCode() );
        transactionType.setName( dto.getName() );

        return transactionType;
    }

    @Override
    public TransactionTypeDTO toTransactionTypeDTO(TransactionType entity) {
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
    public TransactionStatus toTransactionStatus(TransactionStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TransactionStatus transactionStatus = new TransactionStatus();

        transactionStatus.setId( dto.getId() );
        transactionStatus.setCode( dto.getCode() );
        transactionStatus.setName( dto.getName() );

        return transactionStatus;
    }

    @Override
    public TransactionStatusDTO toTransactionStatusDTO(TransactionStatus entity) {
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
    public Bank toBank(BankDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Bank bank = new Bank();

        bank.setId( dto.getId() );
        bank.setName( dto.getName() );
        bank.setBik( dto.getBik() );

        return bank;
    }

    @Override
    public BankDTO toBankDTO(Bank entity) {
        if ( entity == null ) {
            return null;
        }

        BankDTO bankDTO = new BankDTO();

        bankDTO.setId( entity.getId() );
        bankDTO.setName( entity.getName() );
        bankDTO.setBik( entity.getBik() );

        return bankDTO;
    }

    @Override
    public Category toCategory(CategoryDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Category category = new Category();

        category.setId( dto.getId() );
        category.setName( dto.getName() );
        category.setType( dto.getType() );

        return category;
    }

    @Override
    public CategoryDTO toCategoryDTO(Category entity) {
        if ( entity == null ) {
            return null;
        }

        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setId( entity.getId() );
        categoryDTO.setName( entity.getName() );
        categoryDTO.setType( entity.getType() );

        return categoryDTO;
    }
}
