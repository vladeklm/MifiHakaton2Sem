package it.globus.finaudit.DTO.reportfilter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;


@Schema(
        description = "Фильтр для недельного отчета. Период должен быть полной неделей (с понедельника по воскресенье)",
        allOf = PeriodOperationFilter.class,
        example = """
                {
                  "dateFrom": "2025-05-05",
                  "dateTo": "2025-05-11"
                }
                """
)
public class WeeklyOperationFilter extends PeriodOperationFilter {


    @AssertTrue(message = "Период должен составлять ровно одну неделю (с понедельника по воскресенье)")
    @Schema(hidden = true)
    public boolean isWeeklyRangeValid() {
        LocalDate weekStart = dateFrom.with(MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        return dateFrom.equals(weekStart) && dateTo.equals(weekEnd);
    }
}
