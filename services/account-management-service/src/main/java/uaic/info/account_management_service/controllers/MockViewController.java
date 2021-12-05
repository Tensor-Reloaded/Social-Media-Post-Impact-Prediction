package uaic.info.account_management_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uaic.info.account_management_service.services.AccountService;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Controller
@RequiredArgsConstructor
public class MockViewController {
    private final AccountService accountService;

    //starting page for Twitter login demo
    @RequestMapping("/login")
    public String twiterLogin(Model model) {
        return "login";
    }


    //redirect to demo if user hits the root
    @RequestMapping("/")
    public String home(Model model) {
        return "redirect:login";
    }


    @RequestMapping(value = "/account/delete/{id}")
    public String deleteAccount(@PathVariable @Valid @Min(0) Long id) {
        accountService.removeById(id);
        return "login";
    }
}
