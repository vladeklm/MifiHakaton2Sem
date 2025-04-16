package app.structura.finaudit.service.report;

import app.structura.finaudit.entity.Operation;
import app.structura.finaudit.repository.OperationRepository;
import org.springframework.data.jpa.domain.Specification;

public interface ReportGenerator {


    byte[] generateOperationReport(Specification<Operation> criteria);

}
