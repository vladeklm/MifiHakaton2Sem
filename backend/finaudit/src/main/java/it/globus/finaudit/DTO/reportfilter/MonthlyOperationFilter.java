package it.globus.finaudit.DTO.reportfilter;

import it.globus.finaudit.DTO.OperationFilter;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;


public class MonthlyOperationFilter extends OperationFilter {

    @AssertTrue(message = "Период должен составлять ровно один месяц (с 1 по последнее число месяца)")
    public boolean isMonthlyRangeValid() {
        LocalDate monthStart = getDateFrom().withDayOfMonth(1);
        LocalDate monthEnd = monthStart.with(TemporalAdjusters.lastDayOfMonth());
        return getDateFrom().equals(monthStart) && getDateTo().equals(monthEnd);
    }
}
