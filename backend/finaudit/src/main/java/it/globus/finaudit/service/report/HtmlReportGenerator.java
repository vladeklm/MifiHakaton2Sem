package it.globus.finaudit.service.report;

import it.globus.finaudit.repository.OperationRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import org.springframework.stereotype.Component;

import java.io.OutputStream;


@Component
public class HtmlReportGenerator extends AbstractReportGenerator {
    public HtmlReportGenerator(OperationRepository operationRepository, ReportTemplate reportTemplate) {
        super(operationRepository, reportTemplate);
    }

    @Override
    void exportReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        HtmlExporter exporter = new HtmlExporter();
        SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
        configuration.setEmbedImage(true);
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleHtmlExporterOutput(outputStream));
        exporter.exportReport();
    }
}
