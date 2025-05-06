package it.globus.finaudit.DTO;

import lombok.Data;

@Data
public class TransactionStatusDTO {
    private Long id;
    private String code;
    private String name;
}