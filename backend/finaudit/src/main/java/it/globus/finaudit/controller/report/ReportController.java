package it.globus.finaudit.controller.report;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.DTO.reportfilter.*;
import it.globus.finaudit.mapper.OperationFilterMapper;
import it.globus.finaudit.security.UserDetailsImpl;
import it.globus.finaudit.service.report.ReportGeneratorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;
    private final OperationFilterMapper operationFilterMapper;

    public ReportController(ReportGeneratorService reportGeneratorService, OperationFilterMapper operationFilterMapper) {
        this.reportGeneratorService = reportGeneratorService;
        this.operationFilterMapper = operationFilterMapper;
    }

    @PostMapping("/general/{type}")
    public ResponseEntity<byte[]> generateGeneralReport(@PathVariable String type,
                                                        @Valid @RequestBody OperationFilter filter,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        byte[] responseBytes = reportGeneratorService
                .generateGeneralReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "general", type);
    }

    @PostMapping("/pie_chart_income/{type}")
    public ResponseEntity<byte[]> generatePieChartIncomeReport(@PathVariable String type,
                                                               @Valid @RequestBody PeriodOperationFilter periodFilter,
                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generatePieChartIncomeReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "pie_chart_income", type);
    }

    @PostMapping("/pie_chart_withdraw/{type}")
    public ResponseEntity<byte[]> generatePieChartWithdrawReport(@PathVariable String type,
                                                                 @Valid @RequestBody PeriodOperationFilter periodFilter,
                                                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generatePieChartWithdrawReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "pie_chart_withdraw", type);
    }


    @PostMapping("/weekly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateWeeklyDynamicsOperationsReport(@PathVariable String type,
                                                                         @Valid @RequestBody
                                                                         WeeklyOperationFilter periodFilter,
                                                                         @AuthenticationPrincipal
                                                                             UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateWeeklyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "weekly_dynamics_operations", type);
    }

    @PostMapping("/monthly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateMonthlyDynamicsOperationsReport(@PathVariable String type,
                                                                          @Valid @RequestBody
                                                                          MonthlyOperationFilter periodFilter,
                                                                          @AuthenticationPrincipal
                                                                              UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateMonthlyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "monthly_dynamics_operations", type);
    }

    @PostMapping("/quarterly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateQuarterlyDynamicsOperationsReport(@PathVariable String type,
                                                                            @Valid @RequestBody
                                                                            QuarterlyOperationFilter periodFilter,
                                                                            @AuthenticationPrincipal
                                                                                UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateQuarterlyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "quarterly_dynamics_operations", type);
    }

    @PostMapping("/yearly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateYearlyDynamicsOperationsReport(@PathVariable String type,
                                                                         @Valid @RequestBody
                                                                         YearlyOperationFilter periodFilter,
                                                                         @AuthenticationPrincipal
                                                                             UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateYearlyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "yearly_dynamics_operations", type);
    }


    private ResponseEntity<byte[]> getResponseForReport(byte[] responseBytes, String filename, String type) {
        return switch (type.toLowerCase()) {
            case "pdf" -> ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(responseBytes);
            case "xlsx" -> ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".xlsx")
                    .contentType(MediaType
                            .parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                    .body(responseBytes);
            case "html" -> ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename + ".html")
                    .contentType(MediaType.TEXT_HTML)
                    .body(responseBytes);
            case "docx" -> ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename + ".docx")
                    .contentType(MediaType
                            .parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                    .body(responseBytes);
            default -> throw new IllegalArgumentException("Unknown report generator type: " + type);
        };
    }
}
