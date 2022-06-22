package net.hexabrain.hireo.web.job.controller;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hexabrain.hireo.web.account.service.AccountService;
import net.hexabrain.hireo.web.common.security.CurrentUser;
import net.hexabrain.hireo.web.job.dto.JobDetailsResponse;
import net.hexabrain.hireo.web.job.dto.JobSearchRequest;
import net.hexabrain.hireo.web.job.service.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
    private final AccountService accountService;
    private final JobService jobService;

    @GetMapping("/search")
    public String list(
        @CurrentUser User user,
        Model model,
        JobSearchRequest searchRequest
    ) {
        model.addAttribute("request", searchRequest);
        model.addAttribute("results", jobService.search(searchRequest));
        model.addAttribute("account", accountService.findByEmail(user.getUsername()));
        return "jobs/search";
    }

    @GetMapping("/{id}")
    public String job(
        @CurrentUser User user,
        Model model,
        @PathVariable("id") Long id
    ) {
        JobDetailsResponse foundJob = jobService.findOne(id);
        model.addAttribute("company", foundJob.getCompany());
        model.addAttribute("job", foundJob);
        model.addAttribute("account", accountService.findByEmail(user.getUsername()));
        return "jobs/info";
    }
}
