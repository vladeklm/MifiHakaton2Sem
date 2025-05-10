package it.globus.finaudit.DTO.reportfilter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


@Schema(
        description = "Фильтр для годового отчета. Период должен быть полный год (с 1 января по 31 декабря)",
        allOf = PeriodOperationFilter.class,
        example = """
                {
                  "dateFrom": "2025-01-01",
                  "dateTo": "2025-12-31"
                }
                """
)
public class YearlyOperationFilter extends PeriodOperationFilter {

    @AssertTrue(message = "Период должен составлять ровно один год (с 1 января по 31 декабря)")
    @Schema(hidden = true)
    public boolean isYearlyRangeValid() {
        LocalDate yearStart = LocalDate.of(dateFrom.getYear(), 1, 1);
        LocalDate yearEnd = yearStart.with(TemporalAdjusters.lastDayOfYear());
        return dateFrom.equals(yearStart) && dateTo.equals(yearEnd);
    }
}
