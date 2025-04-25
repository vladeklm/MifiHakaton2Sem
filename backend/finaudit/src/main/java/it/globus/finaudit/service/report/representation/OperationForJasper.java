package it.globus.finaudit.service.report.representation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OperationForJasper {
    private long id;
    private String operationType;
    private String operationCategory;
    private String operationStatus;
    private BigDecimal amount;
    private String comment;
}
