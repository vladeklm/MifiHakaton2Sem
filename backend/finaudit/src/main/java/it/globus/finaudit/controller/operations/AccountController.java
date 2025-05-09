package it.globus.finaudit.controller.operations;

import it.globus.finaudit.DTO.AccountInfoDTO;
import it.globus.finaudit.security.UserDetailsImpl;
import it.globus.finaudit.service.operations.AccountService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
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

        return accountService.getAccountsByClient(userDetails.getUser().getId(), isContragentAccount);
    }
}
