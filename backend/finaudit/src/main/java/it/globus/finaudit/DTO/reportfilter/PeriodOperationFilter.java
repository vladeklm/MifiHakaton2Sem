package it.globus.finaudit.DTO.reportfilter;


import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PeriodOperationFilter {
    @NotNull
    LocalDate dateFrom;
    @NotNull
    LocalDate dateTo;

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
