package it.globus.finaudit.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OperationStatusDTO {
    private Long id;

    @NotBlank(message = "Код статуса обязателен")
    private String code;

    @NotBlank(message = "Название статуса обязательно")
    private String name;
}
