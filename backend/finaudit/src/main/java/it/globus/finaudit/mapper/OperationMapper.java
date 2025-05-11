package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.OperationDTO;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.entity.OperationStatus;
import it.globus.finaudit.entity.OperationType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OperationMapper {

    // To DTO
    @Mapping(source = "operationCategory.name", target = "operationCategoryName")
    @Mapping(source = "operationType.name", target = "operationTypeName")
    @Mapping(source = "operationStatus.name", target = "operationStatusName")
    @Mapping(source = "clientType.name", target = "clientTypeName")
    @Mapping(source = "bankAccount.number", target = "bankAccountNumber")
    @Mapping(source = "bankRecipientAccount.number", target = "bankRecipientAccountNumber")
    @Mapping(target = "bankFromName", source = "bankFrom.name")
    @Mapping(target = "bankToName", source = "bankTo.name")
    OperationDTO toDto(Operation operation);

    // To Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "operationType", source = "operationTypeName", qualifiedByName = "codeToType")
    @Mapping(target = "operationStatus", source = "operationStatusName", qualifiedByName = "codeToStatus")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Operation toEntity(OperationDTO dto);

    // Дополнительные методы
    List<OperationDTO> toDtoList(List<Operation> operations);

    @Named("codeToType")
    default OperationType codeToType(String code) {
        if (code == null) return null;
        OperationType type = new OperationType();
        type.setCode(code);
        return type;
    }

    @Named("codeToStatus")
    default OperationStatus codeToStatus(String code) {
        if (code == null) return null;
        OperationStatus status = new OperationStatus();
        status.setCode(code);
        return status;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateOperationFromDTO(OperationDTO dto, @MappingTarget Operation entity);
}