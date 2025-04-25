package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;

public interface ReportGenerator {


    byte[] generateGeneralReport(OperationFilter filter);

    byte[] generatePieChartIncome(OperationFilter filter);

    byte[] generatePieChartWithdraw(OperationFilter filter);

    byte[] generateWeeklyDynamicsOperationsReport(OperationFilter filter);

    byte[] generateMonthlyDynamicsOperationsReport(OperationFilter filter);

    byte[] generateQuarterlyDynamicsOperationsReport(OperationFilter filter);

    byte[] generateYearlyDynamicsOperationsReport(OperationFilter filter);
}
