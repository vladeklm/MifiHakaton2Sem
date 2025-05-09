package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.DTO.CategoryDTO;
import it.globus.finaudit.DTO.ClientTypeDTO;
import it.globus.finaudit.DTO.OperationStatusDTO;
import it.globus.finaudit.DTO.OperationTypeDTO;
import it.globus.finaudit.entity.Bank;
import it.globus.finaudit.entity.Category;
import it.globus.finaudit.entity.ClientType;
import it.globus.finaudit.entity.OperationStatus;
import it.globus.finaudit.entity.OperationType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T23:47:49+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class DictionaryMapperImpl implements DictionaryMapper {

    @Override
    public ClientType toClientType(ClientTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ClientType clientType = new ClientType();

        clientType.setId( dto.getId() );
        clientType.setName( dto.getName() );
        clientType.setCode( dto.getCode() );

        return clientType;
    }

    @Override
    public ClientTypeDTO toClientTypeDTO(ClientType entity) {
        if ( entity == null ) {
            return null;
        }

        ClientTypeDTO clientTypeDTO = new ClientTypeDTO();

        clientTypeDTO.setId( entity.getId() );
        clientTypeDTO.setCode( entity.getCode() );
        clientTypeDTO.setName( entity.getName() );

        return clientTypeDTO;
    }

    @Override
    public OperationType toOperationType(OperationTypeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OperationType operationType = new OperationType();

        operationType.setId( dto.getId() );
        operationType.setName( dto.getName() );
        operationType.setCode( dto.getCode() );

        return operationType;
    }

    @Override
    public OperationTypeDTO toOperationTypeDTO(OperationType entity) {
        if ( entity == null ) {
            return null;
        }

        OperationTypeDTO operationTypeDTO = new OperationTypeDTO();

        operationTypeDTO.setId( entity.getId() );
        operationTypeDTO.setCode( entity.getCode() );
        operationTypeDTO.setName( entity.getName() );

        return operationTypeDTO;
    }

    @Override
    public OperationStatus toOperationStatus(OperationStatusDTO dto) {
        if ( dto == null ) {
            return null;
        }

        OperationStatus operationStatus = new OperationStatus();

        operationStatus.setId( dto.getId() );
        operationStatus.setName( dto.getName() );
        operationStatus.setCode( dto.getCode() );

        return operationStatus;
    }

    @Override
    public OperationStatusDTO toOperationStatusDTO(OperationStatus entity) {
        if ( entity == null ) {
            return null;
        }

        OperationStatusDTO operationStatusDTO = new OperationStatusDTO();

        operationStatusDTO.setId( entity.getId() );
        operationStatusDTO.setCode( entity.getCode() );
        operationStatusDTO.setName( entity.getName() );

        return operationStatusDTO;
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
