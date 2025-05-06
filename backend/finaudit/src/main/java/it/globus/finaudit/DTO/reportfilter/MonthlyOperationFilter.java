package it.globus.finaudit.DTO.reportfilter;


import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


public class MonthlyOperationFilter extends PeriodOperationFilter {

    @AssertTrue(message = "Период должен составлять ровно один месяц (с 1 по последнее число месяца)")
    public boolean isMonthlyRangeValid() {
        LocalDate monthStart = dateFrom.withDayOfMonth(1);
        LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());
        return dateFrom.equals(monthStart) && dateTo.equals(monthEnd);
    }
}
