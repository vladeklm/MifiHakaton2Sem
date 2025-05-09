package it.globus.finaudit.controller.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Report API", description = "API для генерации различных отчетов")
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;
    private final OperationFilterMapper operationFilterMapper;

    public ReportController(ReportGeneratorService reportGeneratorService, OperationFilterMapper operationFilterMapper) {
        this.reportGeneratorService = reportGeneratorService;
        this.operationFilterMapper = operationFilterMapper;
    }


    @Operation(
            summary = "Генерация общего отчета",
            description = "Генерирует отчет в указанном формате на основе фильтра операций",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            }
    )
    @PostMapping("/general/{type}")
    public ResponseEntity<byte[]> generateGeneralReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody OperationFilter filter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        byte[] responseBytes = reportGeneratorService
                .generateGeneralReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "general", type);
    }


    @Operation(
            summary = "Генерация отчета по доходам (круговая диаграмма)",
            description = "Генерирует отчет с круговой диаграммой распределения доходов по категориям",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            }
    )
    @PostMapping("/pie_chart_income/{type}")
    public ResponseEntity<byte[]> generatePieChartIncomeReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody PeriodOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generatePieChartIncomeReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "pie_chart_income", type);
    }


    @Operation(
            summary = "Генерация отчета по расходам (круговая диаграмма)",
            description = "Генерирует отчет с круговой диаграммой распределения расходов по категориям",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )),
                    @ApiResponse(responseCode = "400", description = "Неверные параметры запроса")
            }
    )
    @PostMapping("/pie_chart_withdraw/{type}")
    public ResponseEntity<byte[]> generatePieChartWithdrawReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody PeriodOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generatePieChartWithdrawReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "pie_chart_withdraw", type);
    }

    @Operation(
            summary = "Генерация отчета по недельной динамике операций",
            description = "Генерирует отчет с динамикой операций по неделям",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )),
                    @ApiResponse(responseCode = "400", description = "Период должен составлять ровно одну неделю")
            }
    )
    @PostMapping("/weekly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateWeeklyDynamicsOperationsReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody WeeklyOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateWeeklyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "weekly_dynamics_operations", type);
    }


    @Operation(
            summary = "Генерация отчета по месячной динамике операций",
            description = "Генерирует отчет с динамикой операций по месяцам. Период должен быть полным месяцем (с 1 по последнее число месяца)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный период (должен быть полный месяц с 1 по последнее число)"
                    )
            }
    )
    @PostMapping("/monthly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateMonthlyDynamicsOperationsReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody MonthlyOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateMonthlyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "monthly_dynamics_operations", type);
    }


    @Operation(
            summary = "Генерация отчета по квартальной динамике операций",
            description = "Генерирует отчет с динамикой операций по кварталам. Период должен быть полным кварталом (3 полных месяца)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный период (должен быть полный квартал)"
                    )
            }
    )
    @PostMapping("/quarterly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateQuarterlyDynamicsOperationsReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody QuarterlyOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OperationFilter filter = operationFilterMapper.toOperationFilter(periodFilter);
        byte[] responseBytes = reportGeneratorService
                .generateQuarterlyDynamicsOperationsReport(type, filter, userDetails.getUser().getId());
        return getResponseForReport(responseBytes, "quarterly_dynamics_operations", type);
    }

    @Operation(
            summary = "Генерация отчета по годовой динамике операций",
            description = "Генерирует отчет с динамикой операций по годам. Период должен быть полным годом (с 1 января по 31 декабря)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Успешная генерация отчета",
                            content = @Content(
                                    mediaType = "application/octet-stream",
                                    schema = @Schema(type = "string", format = "binary")
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Неверный период (должен быть полный год)"
                    )
            }
    )
    @PostMapping("/yearly_dynamics_operations/{type}")
    public ResponseEntity<byte[]> generateYearlyDynamicsOperationsReport(
            @Parameter(
                    description = "Тип отчета (pdf, xlsx, html, docx)",
                    example = "pdf",
                    required = true
            )
            @PathVariable String type,
            @Valid @RequestBody YearlyOperationFilter periodFilter,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
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
