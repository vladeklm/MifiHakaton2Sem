package it.globus.finaudit.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonTypeDTO {
    private Long id;

    @NotBlank(message = "Код типа обязателен")
    private String code;

    @NotBlank(message = "Название типа обязательно")
    private String name;
}
