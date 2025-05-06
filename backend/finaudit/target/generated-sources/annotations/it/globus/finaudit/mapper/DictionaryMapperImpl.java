package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.DTO.reportfilter.PersonTypeDTO;
import it.globus.finaudit.DTO.reportfilter.TransactionStatusDTO;
import it.globus.finaudit.DTO.reportfilter.TransactionTypeDTO;
import it.globus.finaudit.entity.Bank;
import it.globus.finaudit.entity.Category;
import it.globus.finaudit.entity.OperationStatus;
import it.globus.finaudit.entity.OperationType;
import it.globus.finaudit.entity.PersonType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-06T01:03:19+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
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
    public OperationType toTransactionType(TransactionTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OperationType operationType = new OperationType();

        operationType.setId( dto.getId() );
        operationType.setCode( dto.getCode() );
        operationType.setName( dto.getName() );

        return operationType;
    }

    @Override
    public TransactionTypeDTO toTransactionTypeDTO(OperationType entity) {
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
    public OperationStatus toTransactionStatus(TransactionStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OperationStatus operationStatus = new OperationStatus();

        operationStatus.setId( dto.getId() );
        operationStatus.setCode( dto.getCode() );
        operationStatus.setName( dto.getName() );

        return operationStatus;
    }

    @Override
    public TransactionStatusDTO toTransactionStatusDTO(OperationStatus entity) {
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
