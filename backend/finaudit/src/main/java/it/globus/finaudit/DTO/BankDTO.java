package it.globus.finaudit.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankDTO {
    private Long id;

    @NotBlank(message = "Название банка обязательно")
    private String name;

    @Pattern(regexp = "\\d{9}", message = "БИК должен содержать 9 цифр")
    private String bik;
}