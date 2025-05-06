package it.globus.finaudit.DTO;

import jakarta.validation.constraints.*;
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

    private Long clientId;

    @PositiveOrZero(message = "BankFromId должен быть положительным или нулевым")
    private Long bankFromId;

    @PositiveOrZero(message = "BankToId должен быть положительным или нулевым")
    private Long bankToId;

    private Long bankRecipientAccountId;

    private Long bankAccountId;

    private String clientTypeName;

    @Size(max = 50, message = "Status must be less than 50 characters")
    private String operationStatusName;

    @Pattern(regexp = "\\d{11}", message = "ИНН должен содержать только 11 цифр")
    private String inn;

    private BigDecimal amount;

    @Pattern(regexp = "^(8|\\+7)\\d{10}$", message = "Телефон должен начинаться с 8 или +7 и содержать 11 цифр")
    private String phoneNumber;

    @Size(max = 50, message = "Operation type must be less than 50 characters")
    private String operationTypeName;

    @Size(max = 50, message = "Operation category must be less than 50 characters")
    private String operationCategoryName;

    @Size(max = 500, message = "Комментарий не должен превышать 500 символов")
    private String comment;

    @NotNull
    @Positive
    private String operationTypeCode;
    private String statusCode;
    private String senderBank;
    private String receiverBank;

    private LocalDateTime dateTimeOperation;
}