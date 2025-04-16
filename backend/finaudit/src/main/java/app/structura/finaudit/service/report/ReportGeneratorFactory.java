package app.structura.finaudit.service.report;

import org.springframework.stereotype.Component;

@Component
public class ReportGeneratorFactory {
    private final PdfReportGenerator pdfReportGenerator;

    public ReportGeneratorFactory(PdfReportGenerator pdfReportGenerator) {
        this.pdfReportGenerator = pdfReportGenerator;
    }

    public ReportGenerator getReportGenerator(String generatorType) {
        return switch (generatorType.toLowerCase()) {
            case "pdf" -> pdfReportGenerator;
            default -> throw new IllegalArgumentException("Unknown report generator type: " + generatorType);
        };
    }

}
