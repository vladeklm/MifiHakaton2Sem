package it.globus.finaudit.util.report;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DateHelper {
    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter monthFormat = DateTimeFormatter.ofPattern("MM.yyyy");
    public static final DateTimeFormatter dateAndTimeFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public static List<String> generatingAllDaysOfWeek(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> date.format(dateFormat))
                .collect(Collectors.toList());
    }

    public static List<String> generatingAllWeeksOfMonth(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
                .map(date -> date.format(dateFormat))
                .collect(Collectors.toList());
    }

    public static List<String> generatingAllMonthOfQuarter(LocalDate startDate, LocalDate endDate) {
        List<String> allMonths = new ArrayList<>();
        LocalDate start = startDate.withDayOfMonth(1);
        LocalDate end = endDate.withDayOfMonth(1);
        while (!start.isAfter(end)) {
            allMonths.add(start.format(monthFormat));
            start = start.plusMonths(1);
        }
        return allMonths;
    }

    public static List<String> generatingAllMonthOfYear(LocalDate startDate, LocalDate endDate) {
        List<String> allMonthsOfYear  = new ArrayList<>();
        LocalDate start = startDate.withMonth(1).withDayOfMonth(1);
        LocalDate end = endDate.withMonth(12).withDayOfMonth(31);
        while (!start.isAfter(end)) {
            allMonthsOfYear.add(start.format(monthFormat));
            start = start.plusMonths(1);
        }
        return allMonthsOfYear;
    }
}
