package it.globus.finaudit.DTO;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperationDTO {

    private Long id;

    private String bankFromName;

    private String bankToName;

    private Long bankRecipientAccountNumber;

    private Long bankAccountNumber;

    private String clientTypeName;

    private String operationStatusName;

    private String operationTypeName;

    private String operationCategoryName;

    @Pattern(regexp = "^(8|\\+7)\\d{10}$",
            message = "Телефон должен начинаться с 8 или +7 и содержать 11 цифр (например: 89991234567 или +79991234567)")
    private String phoneNumber;

    @Pattern(regexp = "\\d{11}", message = "ИНН должен содержать только 11 цифр")
    private String inn;

    private BigDecimal amount;

    @Size(max = 500, message = "Комментарий не должен превышать 500 символов")
    private String comment;

    private LocalDateTime dateTimeOperation;
}