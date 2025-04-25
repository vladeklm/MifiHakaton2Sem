package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;
import org.springframework.stereotype.Service;


@Service
public class ReportGeneratorService {
    private final ReportGeneratorFactory reportGeneratorFactory;

    public ReportGeneratorService(ReportGeneratorFactory reportGeneratorFactory) {
        this.reportGeneratorFactory = reportGeneratorFactory;
    }

    public byte[] generateGeneralReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateGeneralReport(filter);
    }

    public byte[] generatePieChartIncomeReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generatePieChartIncome(filter);
    }

    public byte[] generatePieChartWithdrawReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generatePieChartWithdraw(filter);
    }

    public byte[] generateWeeklyDynamicsOperationsReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateWeeklyDynamicsOperationsReport(filter);
    }

    public byte[] generateMonthlyDynamicsOperationsReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateMonthlyDynamicsOperationsReport(filter);
    }

    public byte[] generateQuarterlyDynamicsOperationsReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateQuarterlyDynamicsOperationsReport(filter);
    }

    public byte[] generateYearlyDynamicsOperationsReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateYearlyDynamicsOperationsReport(filter);
    }
}
