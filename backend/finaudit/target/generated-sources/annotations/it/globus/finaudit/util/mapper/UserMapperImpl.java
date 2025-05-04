package it.globus.finaudit.util.mapper;

import it.globus.finaudit.DTO.AuthenticationDTO;
import it.globus.finaudit.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-04T23:31:07+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(AuthenticationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setLogin( dto.getLogin() );
        user.setPassword( dto.getPassword() );

        return user;
    }
}
