package it.globus.finaudit.DTO.reportfilter;

import it.globus.finaudit.DTO.OperationFilter;
import jakarta.validation.constraints.AssertTrue;

import java.time.LocalDate;

import static java.time.DayOfWeek.MONDAY;

public class WeeklyOperationFilter extends OperationFilter {

    @AssertTrue(message = "Период должен составлять ровно одну неделю (с понедельника по воскресенье)")
    public boolean isWeeklyRangeValid() {
        LocalDate weekStart = getDateFrom().with(MONDAY);
        LocalDate weekEnd = weekStart.plusDays(6);
        return getDateFrom().equals(weekStart) && getDateTo().equals(weekEnd);
    }
}
