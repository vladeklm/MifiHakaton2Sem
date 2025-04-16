package app.structura.finaudit.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationFilter {

    @PositiveOrZero(message = "BankFromId должен быть положительным или нулевым")
    private Long bankFromId;
    @PositiveOrZero(message = "BankToId должен быть положительным или нулевым")
    private Long bankToId;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    @Size(max = 50, message = "Status must be less than 50 characters")
    private String status;

    @Pattern(regexp = "\\d{11}", message = "ИНН должен содержать только 11 цифр")
    private String inn;

    @DecimalMin(value = "0.0", inclusive = false, message = "Min amount must be greater than 0")
    private BigDecimal minAmount;

    @DecimalMin(value = "0.0", inclusive = false, message = "Max amount must be greater than 0")
    private BigDecimal maxAmount;

    @Size(max = 50, message = "Operation type must be less than 50 characters")
    private String operationType;

    @Size(max = 50, message = "Operation category must be less than 50 characters")
    private String operationCategory;

    @AssertTrue(message = "dateFrom должна быть предшествующей dateTo или равной ей")
    public boolean isDateRangeValid() {
        if (dateFrom == null || dateTo == null) {
            return true;
        }
        try {
            return !dateFrom.isAfter(dateTo);
        } catch (Exception e) {
            return false;
        }
    }
}
