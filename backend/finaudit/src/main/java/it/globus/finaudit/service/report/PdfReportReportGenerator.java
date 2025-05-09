package it.globus.finaudit.service.report;

import it.globus.finaudit.repository.OperationRepository;
import it.globus.finaudit.repository.OperationTypeRepository;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.stereotype.Component;

import java.io.OutputStream;

@Component
public class PdfReportReportGenerator extends AbstractReportGenerator {


    public PdfReportReportGenerator(OperationRepository operationRepository, ReportTemplate reportTemplate,
                                    OperationTypeRepository operationTypeRepository) {
        super(operationRepository, reportTemplate, operationTypeRepository);
    }

    @Override
    void exportReport(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
    }
}
