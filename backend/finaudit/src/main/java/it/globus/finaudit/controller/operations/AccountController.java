package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.AccountInfoDTO;
import it.globus.finaudit.security.UserDetailsImpl;
import it.globus.finaudit.service.operations.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<AccountInfoDTO> getAccounts(
            @RequestParam boolean isContragentAccount,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Long clientId = userDetails.getUser().getId();
        return accountService.getAccountsByClient(clientId, isContragentAccount);
    }
}