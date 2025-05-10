package it.globus.finaudit.DTO;


import it.globus.finaudit.util.validation.annotation.UniqueUsername;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationDTO {

    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 3, max = 50, message = "Логин должен быть от 3 до 50 символов")
    @UniqueUsername
    private String login;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, max = 50, message = "Пароль должен быть от 8 до 50 символов")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String firstName;

    @Size(min = 2, max = 50, message = "Отчество должно быть от 2 до 50 символов")
    private String patronymic;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(min = 2, max = 50, message = "Фамилия должна быть от 2 до 50 символов")
    private String secondName;
}
