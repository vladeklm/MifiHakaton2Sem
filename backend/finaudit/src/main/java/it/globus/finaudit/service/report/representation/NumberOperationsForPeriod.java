package it.globus.finaudit.service.report.representation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NumberOperationsForPeriod {
    private String typeCategory;
    private Long countOperation;
    private String datePeriod;
}
