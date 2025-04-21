package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;
import it.globus.finaudit.entity.Operation;
import it.globus.finaudit.repository.specifications.OperationSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ReportGeneratorService {
    private final ReportGeneratorFactory reportGeneratorFactory;

    public ReportGeneratorService(ReportGeneratorFactory reportGeneratorFactory) {
        this.reportGeneratorFactory = reportGeneratorFactory;
    }

    public byte[] generateOperationReport(String type, OperationFilter filter) {
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateOperationReport(filter);
    }
}
