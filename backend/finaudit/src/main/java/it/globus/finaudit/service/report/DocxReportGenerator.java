package it.globus.finaudit.service.report;

import it.globus.finaudit.repository.OperationRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class DocxReportGenerator extends AbstractReportGenerator {
    public DocxReportGenerator(OperationRepository operationRepository, ReportTemplate reportTemplate) {
        super(operationRepository, reportTemplate);
    }

    @Override
    void exportReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRDocxExporter exporter = new JRDocxExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
    }
}
