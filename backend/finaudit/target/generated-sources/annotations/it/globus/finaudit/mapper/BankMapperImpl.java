package it.globus.finaudit.mapper;

import it.globus.finaudit.DTO.BankDTO;
import it.globus.finaudit.entity.Bank;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-09T00:16:40+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class BankMapperImpl implements BankMapper {

    @Override
    public Bank toEntity(BankDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Bank bank = new Bank();

        bank.setName( dto.getName() );
        bank.setBik( dto.getBik() );

        return bank;
    }

    @Override
    public BankDTO toDTO(Bank entity) {
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
    public void updateEntity(BankDTO dto, Bank entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getBik() != null ) {
            entity.setBik( dto.getBik() );
        }
    }
}
