package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;

public interface ReportGenerator {


    byte[] generateGeneralReport(OperationFilter filter, Long userId);

    byte[] generatePieChartIncome(OperationFilter filter, Long userId);

    byte[] generatePieChartWithdraw(OperationFilter filter, Long userId);

    byte[] generateWeeklyDynamicsOperationsReport(OperationFilter filter, Long userId);

    byte[] generateMonthlyDynamicsOperationsReport(OperationFilter filter, Long userId);

    byte[] generateQuarterlyDynamicsOperationsReport(OperationFilter filter, Long userId);

    byte[] generateYearlyDynamicsOperationsReport(OperationFilter filter, Long userId);
}
