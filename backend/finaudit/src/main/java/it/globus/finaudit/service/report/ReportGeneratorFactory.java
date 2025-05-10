package it.globus.finaudit.service.report;

import org.springframework.stereotype.Component;

@Component
public class ReportGeneratorFactory {
    private final PdfReportReportGenerator pdfReportGenerator;
    private final XlsxReportGenerator xlsxReportGenerator;
    private final HtmlReportGenerator htmlReportGenerator;
    private final DocxReportGenerator docxReportGenerator;

    public ReportGeneratorFactory(PdfReportReportGenerator pdfReportGenerator,
                                  XlsxReportGenerator xlsxReportGenerator, HtmlReportGenerator htmlReportGenerator, DocxReportGenerator docxReportGenerator) {
        this.pdfReportGenerator = pdfReportGenerator;
        this.xlsxReportGenerator = xlsxReportGenerator;
        this.htmlReportGenerator = htmlReportGenerator;
        this.docxReportGenerator = docxReportGenerator;
    }

    public ReportGenerator getReportGenerator(String generatorType) {
        return switch (generatorType.toLowerCase()) {
            case "pdf" -> pdfReportGenerator;
            case "xlsx" -> xlsxReportGenerator;
            case "html" -> htmlReportGenerator;
            case "docx" -> docxReportGenerator;
            default -> throw new IllegalArgumentException("Unknown report generator type: " + generatorType);
        };
    }

}
