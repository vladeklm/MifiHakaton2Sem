package it.globus.finaudit.DTO.reportfilter;

import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;

public class WeeklyOperationFilter extends PeriodOperationFilter {


    @AssertTrue(message = "Период должен составлять ровно одну неделю (с понедельника по воскресенье)")
    public boolean isWeeklyRangeValid() {
        LocalDate weekStart = dateFrom.with(MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        return dateFrom.equals(weekStart) && dateTo.equals(weekEnd);
    }
}
