package net.hexabrain.hireo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.domain.Company;
import net.hexabrain.hireo.service.CompanyService;
import net.hexabrain.hireo.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyProfileController {
    private final CompanyService companyService;
    private final ReviewService reviewService;

    @GetMapping("/profile/{id}")
    public String profile(@PathVariable("id") Long id, Model model) {
        Optional<Company> foundCompany = companyService.findOne(id);
        if (foundCompany.isPresent()) {
            model.addAttribute("company", foundCompany.get());
            model.addAttribute("reviews", foundCompany.get().getReviews());
            return "company/profile";
        } else {
            return "redirect:/error/404";
        }
    }
}
