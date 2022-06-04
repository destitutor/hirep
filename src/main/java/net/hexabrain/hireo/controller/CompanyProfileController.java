package net.hexabrain.hireo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.domain.Account;
import net.hexabrain.hireo.domain.Company;
import net.hexabrain.hireo.service.AccountService;
import net.hexabrain.hireo.service.CompanyService;
import net.hexabrain.hireo.service.ReviewService;
import net.hexabrain.hireo.utils.HangulUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

@Slf4j
@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyProfileController {
    private final CompanyService companyService;
    private final ReviewService reviewService;
    private final AccountService accountService;

    @GetMapping("/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        if (companyService.isExist(id)) {
            Company foundCompany = companyService.findOne(id);
            model.addAttribute("company", foundCompany);
            model.addAttribute("reviews", foundCompany.getReviews());

            Account currentAccount = accountService.getCurrentAccount();
            model.addAttribute("account", currentAccount);

            return "company/profile";
        } else {
            return "redirect:/error/404";
        }
    }

    @GetMapping("/list")
    public String list(String category) {
        if (StringUtils.isEmptyOrWhitespace(category) || !HangulUtils.isChosung(category.charAt(0))) {
            category = "ㄱ";
        }


        return "company/list";
    }
}
