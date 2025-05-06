package it.globus.finaudit.DTO;

import it.globus.finaudit.entity.Operation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    @Mapping(source = "client.user.id", target = "clientId")
    @Mapping(source = "operationCategory.name", target = "operationCategoryName")
    @Mapping(source = "operationType.name", target = "operationTypeName")
    @Mapping(source = "operationStatus.name", target = "operationStatusName")
    @Mapping(source = "clientType.name", target = "clientTypeName")
    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    OperationDto toDto(Operation operation);

    List<OperationDto> toDtoList(List<Operation> operations);
}