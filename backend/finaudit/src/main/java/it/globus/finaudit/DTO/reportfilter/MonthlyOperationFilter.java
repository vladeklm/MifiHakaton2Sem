package it.globus.finaudit.DTO.reportfilter;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Schema(
        description = "Фильтр для месячного отчета. Период должен быть полным месяцем (с 1 по последнее число месяца)",
        allOf = PeriodOperationFilter.class,
        example = """
                {
                  "dateFrom": "2025-05-01",
                  "dateTo": "2025-05-31"
                }
                """
)
public class MonthlyOperationFilter extends PeriodOperationFilter {

    @AssertTrue(message = "Период должен составлять ровно один месяц (с 1 по последнее число месяца)")
    @Schema(hidden = true)
    public boolean isMonthlyRangeValid() {
        LocalDate monthStart = dateFrom.withDayOfMonth(1);
        LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());
        return dateFrom.equals(monthStart) && dateTo.equals(monthEnd);
    }
}
