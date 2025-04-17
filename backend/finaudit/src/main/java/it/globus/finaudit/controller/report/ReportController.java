package it.globus.finaudit.controller.report;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.service.report.ReportGeneratorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/report")
public class ReportController {
    private final ReportGeneratorService reportGeneratorService;

    public ReportController(ReportGeneratorService reportGeneratorService) {
        this.reportGeneratorService = reportGeneratorService;
    }


    @PostMapping("/generate/{type}")
    public ResponseEntity<byte[]> generate(@PathVariable String type, @Valid @RequestBody OperationFilter filter) {
        byte[] pdfBytes = reportGeneratorService.generateOperationReport(type, filter);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=operation_report.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
