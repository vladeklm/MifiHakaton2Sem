package it.globus.finaudit.DTO;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDTO {
    private Long id;

    @NotBlank(message = "Тип обязателен")
    @Pattern(regexp = "INCOME|EXPENSE", message = "Тип должен быть INCOME или EXPENSE")
    private String type;

    @NotNull(message = "Сумма обязательна")
    @Positive(message = "Сумма должна быть положительной")
    private BigDecimal amount;

    @NotNull(message = "Дата обязательна")
    private LocalDateTime date;

    @NotBlank(message = "Статус обязателен")
    private String status;

    private String senderBank;
    private String receiverBank;

    @Pattern(regexp = "\\d{11}", message = "ИНН должен содержать 11 цифр")
    private String inn;

    private String accountNumber;

    @Pattern(regexp = "^[+7|8]\\d{10}$", message = "Телефон должен начинаться с +7 или 8")
    private String phone;

    private String comment;
    private Long categoryId;
}
