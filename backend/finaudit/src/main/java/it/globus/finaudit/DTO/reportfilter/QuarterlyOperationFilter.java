package it.globus.finaudit.DTO.reportfilter;

import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class QuarterlyOperationFilter extends PeriodOperationFilter {



    @AssertTrue(message = "Период должен составлять ровно один квартал (3 полных месяца)")
    public boolean isQuarterlyRangeValid() {
        int startMonth = dateFrom.getMonthValue();
        int quarterStartMonth = ((startMonth - 1) / 3) * 3 + 1;
        LocalDate quarterStart = LocalDate.of(dateFrom.getYear(), quarterStartMonth, 1);
        LocalDate quarterEnd = quarterStart.plusMonths(2).with(TemporalAdjusters.lastDayOfMonth());
        return dateFrom.equals(quarterStart) && dateTo.equals(quarterEnd);
    }
}
