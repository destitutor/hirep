package net.hexabrain.hireo.controller;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.service.AccountService;
import net.hexabrain.hireo.service.CompanyService;
import net.hexabrain.hireo.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final AccountService accountService;
    private final JobService jobService;
    private final CompanyService companyService;

    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("jobCount", jobService.count());
        model.addAttribute("companyCount", companyService.count());
        model.addAttribute("accountCount", accountService.count());
        return "index";
    }
}
