package net.hexabrain.hireo.web.account.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.account.domain.Account;
import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.account.dto.AccountDto;
import net.hexabrain.hireo.web.account.dto.mapper.AccountMapper;
import net.hexabrain.hireo.web.account.service.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/new")
    public String signUp(Model model) {
        AccountDto account = new AccountDto();
        account.setType(AccountType.FREELANCER);
        model.addAttribute("account", account);
        return "new";
    }

    @PostMapping("/new")
    public String signUp(@Valid @ModelAttribute("account") AccountDto account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors: {}", bindingResult);
            return "new";
        }

        try {
            accountService.save(accountMapper.toEntity(account));
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create account", e);
            bindingResult.reject("exists.email");
            return "new";
        }
        return "redirect:/account/login";
    }

    @GetMapping("/manage")
    public String settings(Model model) {
        Account currentAccount = accountService.getCurrentAccount();
        AccountDto account = accountMapper.toDto(currentAccount);
        model.addAttribute("account", account);
        return "account/manage";
    }
}
