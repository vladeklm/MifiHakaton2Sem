package it.globus.finaudit.DTO.reportfilter;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Schema(
        description = "Базовый фильтр по периоду времени",
        example = """
    {
      "dateFrom": "2025-05-01",
      "dateTo": "2025-05-15"
    }
    """
)
@Data
public class PeriodOperationFilter {
    @Schema(
            description = "Начальная дата периода",
            type = "string",
            format = "date",
            example = "2025-01-01"
    )
    @NotNull
    LocalDate dateFrom;

    @Schema(
            description = "Конечная дата периода",
            type = "string",
            format = "date",
            example = "2025-12-31"
    )
    @NotNull
    LocalDate dateTo;

    @AssertTrue(message = "dateFrom должна быть предшествующей dateTo или равной ей")
    @Schema(hidden = true)
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
