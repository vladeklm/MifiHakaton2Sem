package it.globus.finaudit.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountInfoDTO {
    private String bankAccount;
    private String bankName;
    private Long usageCount;
    }