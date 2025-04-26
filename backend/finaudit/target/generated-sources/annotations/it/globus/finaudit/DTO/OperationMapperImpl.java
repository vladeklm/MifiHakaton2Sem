package it.globus.finaudit.DTO;

import it.globus.finaudit.entity.BankAccount;
import it.globus.finaudit.entity.Client;
import it.globus.finaudit.entity.ClientType;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.entity.OperationCategory;
import it.globus.finaudit.entity.OperationStatus;
import it.globus.finaudit.entity.OperationType;
import it.globus.finaudit.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-26T18:17:52+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.7 (Microsoft)"
)
@Component
public class OperationMapperImpl implements OperationMapper {

    @Override
    public OperationDto toDto(Operation operation) {
        if ( operation == null ) {
            return null;
        }

        OperationDto.OperationDtoBuilder operationDto = OperationDto.builder();

        operationDto.clientId( operationClientUserId( operation ) );
        operationDto.operationCategoryName( operationOperationCategoryName( operation ) );
        operationDto.operationTypeName( operationOperationTypeName( operation ) );
        operationDto.operationStatusName( operationOperationStatusName( operation ) );
        operationDto.clientTypeName( operationClientTypeName( operation ) );
        operationDto.bankAccountId( operationBankAccountId( operation ) );
        operationDto.bankFromId( operation.getBankFromId() );
        operationDto.bankToId( operation.getBankToId() );
        operationDto.bankRecipientAccountId( operation.getBankRecipientAccountId() );
        operationDto.inn( operation.getInn() );
        operationDto.amount( operation.getAmount() );
        operationDto.phoneNumber( operation.getPhoneNumber() );
        operationDto.comment( operation.getComment() );
        operationDto.dateTimeOperation( operation.getDateTimeOperation() );

        return operationDto.build();
    }

    @Override
    public List<OperationDto> toDtoList(List<Operation> operations) {
        if ( operations == null ) {
            return null;
        }

        List<OperationDto> list = new ArrayList<OperationDto>( operations.size() );
        for ( Operation operation : operations ) {
            list.add( toDto( operation ) );
        }

        return list;
    }

    private Long operationClientUserId(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        Client client = operation.getClient();
        if ( client == null ) {
            return null;
        }
        User user = client.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String operationOperationCategoryName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        OperationCategory operationCategory = operation.getOperationCategory();
        if ( operationCategory == null ) {
            return null;
        }
        String name = operationCategory.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String operationOperationTypeName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        OperationType operationType = operation.getOperationType();
        if ( operationType == null ) {
            return null;
        }
        String name = operationType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String operationOperationStatusName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        OperationStatus operationStatus = operation.getOperationStatus();
        if ( operationStatus == null ) {
            return null;
        }
        String name = operationStatus.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String operationClientTypeName(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        ClientType clientType = operation.getClientType();
        if ( clientType == null ) {
            return null;
        }
        String name = clientType.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long operationBankAccountId(Operation operation) {
        if ( operation == null ) {
            return null;
        }
        BankAccount bankAccount = operation.getBankAccount();
        if ( bankAccount == null ) {
            return null;
        }
        Long id = bankAccount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
