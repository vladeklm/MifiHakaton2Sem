package it.globus.finaudit.mapper;


import it.globus.finaudit.DTO.*;
import it.globus.finaudit.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {


    ClientType toClientType(ClientTypeDTO dto);
    ClientTypeDTO toClientTypeDTO(ClientType entity);


    OperationType toOperationType(OperationTypeDTO dto);
    OperationTypeDTO toOperationTypeDTO(OperationType entity);


    OperationStatus toOperationStatus(OperationStatusDTO dto);
    OperationStatusDTO toOperationStatusDTO(OperationStatus entity);


    Bank toBank(BankDTO dto);
    BankDTO toBankDTO(Bank entity);


    Category toCategory(CategoryDTO dto);
    CategoryDTO toCategoryDTO(Category entity);


    default void updateClientTypeFromDTO(ClientTypeDTO dto, @MappingTarget ClientType entity) {
        if (dto == null) return;
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }

    default void updateOperationTypeFromDTO(OperationTypeDTO dto, @MappingTarget OperationType entity) {
        if (dto == null) return;
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }

    default void updateOperationStatusFromDTO(OperationStatusDTO dto, @MappingTarget OperationStatus entity) {
        if (dto == null) return;
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }

    default void updateBankFromDTO(BankDTO dto, @MappingTarget Bank entity) {
        if (dto == null) return;
        entity.setName(dto.getName());
        entity.setBik(dto.getBik());
    }

    default void updateCategoryFromDTO(CategoryDTO dto, @MappingTarget Category entity) {
        if (dto == null) return;
        entity.setName(dto.getName());
        entity.setType(dto.getType());
    }
}
