package it.globus.finaudit.mapper;


import it.globus.finaudit.DTO.*;
import it.globus.finaudit.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DictionaryMapper {


    PersonType toPersonType(PersonTypeDTO dto);
    PersonTypeDTO toPersonTypeDTO(PersonType entity);


    TransactionType toTransactionType(TransactionTypeDTO dto);
    TransactionTypeDTO toTransactionTypeDTO(TransactionType entity);


    TransactionStatus toTransactionStatus(TransactionStatusDTO dto);
    TransactionStatusDTO toTransactionStatusDTO(TransactionStatus entity);


    Bank toBank(BankDTO dto);
    BankDTO toBankDTO(Bank entity);


    Category toCategory(CategoryDTO dto);
    CategoryDTO toCategoryDTO(Category entity);


    default void updatePersonTypeFromDTO(PersonTypeDTO dto, @MappingTarget PersonType entity) {
        if (dto == null) return;
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }

    default void updateTransactionTypeFromDTO(TransactionTypeDTO dto, @MappingTarget TransactionType entity) {
        if (dto == null) return;
        entity.setCode(dto.getCode());
        entity.setName(dto.getName());
    }

    default void updateTransactionStatusFromDTO(TransactionStatusDTO dto, @MappingTarget TransactionStatus entity) {
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
