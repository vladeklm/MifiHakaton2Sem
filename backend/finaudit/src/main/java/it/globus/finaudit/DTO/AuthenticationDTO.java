package it.globus.finaudit.DTO;


import it.globus.finaudit.util.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {

    @NotEmpty(message = "Username cannot be blank")
    @Size(min = 2, max = 50, message = "Username must be between 3 and 50 characters")
    @UniqueUsername
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8,max = 50, message = "Password must be at least 8 characters")
    private String password;
}
