package it.globus.finaudit.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CategoryDTO {
    private Long id;

    @NotBlank(message = "Название категории обязательно")
    private String name;

    @Pattern(regexp = "INCOME|EXPENSE", message = "Тип должен быть INCOME или EXPENSE")
    private String type;
}
