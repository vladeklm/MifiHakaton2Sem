package it.globus.finaudit.service.report.representation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AmountByCategory {
    private String nameCategory;
    private BigDecimal amount;
}
