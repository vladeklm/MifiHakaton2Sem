package it.globus.finaudit.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OperationTypeDTO {
    private Long id;

    @NotBlank(message = "Код типа транзакции обязателен")
    private String code;

    @NotBlank(message = "Название типа обязательно")
    private String name;
}
