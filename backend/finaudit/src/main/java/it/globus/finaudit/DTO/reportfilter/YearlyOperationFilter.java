package it.globus.finaudit.DTO.reportfilter;

import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

public class YearlyOperationFilter  extends PeriodOperationFilter {


    @AssertTrue(message = "Период должен составлять ровно один год (с 1 января по 31 декабря)")
    public boolean isYearlyRangeValid() {
        LocalDate yearStart = LocalDate.of(dateFrom.getYear(), 1, 1);
        LocalDate yearEnd = yearStart.with(TemporalAdjusters.lastDayOfYear());
        return dateFrom.equals(yearStart) && dateTo.equals(yearEnd);
    }
}
