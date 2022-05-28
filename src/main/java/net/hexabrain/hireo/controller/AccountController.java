package net.hexabrain.hireo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.transport.mapper.AccountMapper;
import net.hexabrain.hireo.transport.dto.AccountDto;
import net.hexabrain.hireo.domain.AccountType;
import net.hexabrain.hireo.service.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        AccountDto account = new AccountDto();
        account.setType(AccountType.FREELANCER);
        model.addAttribute("account", account);
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Valid @ModelAttribute("account") AccountDto account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors: {}", bindingResult);
            return "sign-up";
        }

        if (!account.getPassword().equals(account.getPasswordConfirm())) {
            bindingResult.reject("not-match.password");
            return "sign-up";
        }

        try {
            accountService.save(accountMapper.toEntity(account));
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to create account", e);
            bindingResult.reject("exists.email");
            return "sign-up";
        }
        return "redirect:/login";
    }
}
