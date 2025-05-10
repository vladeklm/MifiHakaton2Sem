package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;
import org.springframework.stereotype.Service;


@Service
public class ReportGeneratorService {
    private final ReportGeneratorFactory reportGeneratorFactory;

    public ReportGeneratorService(ReportGeneratorFactory reportGeneratorFactory) {
        this.reportGeneratorFactory = reportGeneratorFactory;
    }

    public byte[] generateGeneralReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateGeneralReport(filter, userId);
    }

    public byte[] generatePieChartIncomeReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generatePieChartIncome(filter, userId);
    }

    public byte[] generatePieChartWithdrawReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generatePieChartWithdraw(filter, userId);
    }

    public byte[] generateWeeklyDynamicsOperationsReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateWeeklyDynamicsOperationsReport(filter, userId);
    }

    public byte[] generateMonthlyDynamicsOperationsReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateMonthlyDynamicsOperationsReport(filter, userId);
    }

    public byte[] generateQuarterlyDynamicsOperationsReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateQuarterlyDynamicsOperationsReport(filter, userId);
    }

    public byte[] generateYearlyDynamicsOperationsReport(String type, OperationFilter filter, Long userId) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateYearlyDynamicsOperationsReport(filter, userId);
    }
}
