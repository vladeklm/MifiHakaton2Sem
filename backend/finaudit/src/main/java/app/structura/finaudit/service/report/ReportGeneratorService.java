package app.structura.finaudit.service.report;

import app.structura.finaudit.DTO.OperationFilter;
import app.structura.finaudit.entity.Operation;
import app.structura.finaudit.repository.specifications.OperationSpecificationBuilder;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
public class ReportGeneratorService {
    private final ReportGeneratorFactory reportGeneratorFactory;

    public ReportGeneratorService(ReportGeneratorFactory reportGeneratorFactory) {
        this.reportGeneratorFactory = reportGeneratorFactory;
    }

    public byte[] generateOperationReport(String type, OperationFilter filter) {
        Specification<Operation> spec = OperationSpecificationBuilder.buildFromFilter(filter);
        ReportGenerator reportGenerator = reportGeneratorFactory.getReportGenerator(type);
        return reportGenerator.generateOperationReport(spec);
    }
}
