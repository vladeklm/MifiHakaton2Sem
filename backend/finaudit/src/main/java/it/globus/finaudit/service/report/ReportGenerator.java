package it.globus.finaudit.service.report;

import it.globus.finaudit.DTO.OperationFilter;

public interface ReportGenerator {


    byte[] generateOperationReport(OperationFilter filter);

}
