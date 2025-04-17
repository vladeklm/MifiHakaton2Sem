package it.globus.finaudit.service.report;

import it.globus.finaudit.entity.Operation;
import org.springframework.data.jpa.domain.Specification;

public interface ReportGenerator {


    byte[] generateOperationReport(Specification<Operation> criteria);

}
