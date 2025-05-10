package it.globus.finaudit.DTO.reportfilter;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


@Schema(
        description = "Фильтр для квартального отчета. Период должен быть ровно один квартал (3 полных месяца)",
        allOf = PeriodOperationFilter.class,
        example = """
        {
          "dateFrom": "2025-01-01",
          "dateTo": "2025-03-31"
        }
        """
)
public class QuarterlyOperationFilter extends PeriodOperationFilter {


    @AssertTrue(message = "Период должен составлять ровно один квартал (3 полных месяца)")
    @Schema(hidden = true)
    public boolean isQuarterlyRangeValid() {
        int startMonth = dateFrom.getMonthValue();
        int quarterStartMonth = ((startMonth - 1) / 3) * 3 + 1;
        LocalDate quarterStart = LocalDate.of(dateFrom.getYear(), quarterStartMonth, 1);
        LocalDate quarterEnd = quarterStart.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
        return dateFrom.equals(quarterStart) && dateTo.equals(quarterEnd);
    }
}
