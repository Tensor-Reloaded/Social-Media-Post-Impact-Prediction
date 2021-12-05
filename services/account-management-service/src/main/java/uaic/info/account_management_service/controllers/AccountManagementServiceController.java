package uaic.info.account_management_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import uaic.info.account_management_service.entities.Account;
import uaic.info.account_management_service.services.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
public class AccountManagementServiceController {
    private final AccountService accountService;

    @GetMapping("/{id}")
    public Account getById(@PathVariable @Valid @Min(0) Long id) {
        return accountService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeById(@PathVariable @Valid @Min(0) Long id) {
        accountService.removeById(id);
    }

}
