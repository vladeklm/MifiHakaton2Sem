package it.globus.finaudit.DTO.reportfilter;

import lombok.Data;

@Data
public class TransactionTypeDTO {
    private Long id;
    private String code;
    private String name;
}