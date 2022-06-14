package net.hexabrain.hireo.web.company.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import net.hexabrain.hireo.utils.HangulUtils;
import net.hexabrain.hireo.web.account.service.AccountService;
import net.hexabrain.hireo.web.company.dto.CompanyProfileResponse;
import net.hexabrain.hireo.web.company.service.CompanyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final AccountService accountService;

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        if (!companyService.isExist(id)) {
            return "redirect:/error/404";
        }

        CompanyProfileResponse foundCompany = companyService.findOne(id);
        model.addAttribute("company", foundCompany);
        model.addAttribute("reviews", foundCompany.getReviews());
        model.addAttribute("account", accountService.getCurrentAccount());
        return "company/profile";
    }

    @GetMapping("/list/{category}")
    public String list(@PathVariable String category, Model model) {
        if (StringUtils.isEmptyOrWhitespace(category) || !HangulUtils.isHangul(category.charAt(0))) {
            category = "ㄱ";
        }

        char firstConsonant = HangulUtils.getFirstConsonant(category.charAt(0));
        model.addAttribute("companies", companyService.list(firstConsonant));
        return "company/list";
    }
}
