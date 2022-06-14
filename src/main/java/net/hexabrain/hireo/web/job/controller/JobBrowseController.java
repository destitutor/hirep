package net.hexabrain.hireo.web.job.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import net.hexabrain.hireo.web.account.service.AccountService;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.SearchRequest;
import net.hexabrain.hireo.web.job.service.JobService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
    private final AccountService accountService;
    private final JobService jobService;

    @GetMapping("/search")
    public String list(Model model, SearchRequest searchRequest,
                       @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("results", jobService.search(searchRequest, page));
        model.addAttribute("account", accountService.getCurrentAccount());
        return "jobs/search";
    }

    @GetMapping("/{id}")
    public String job(Model model, @PathVariable("id") Long id) {
        Job foundJob = jobService.findOne(id);
        model.addAttribute("company", foundJob.getCompany());
        model.addAttribute("job", foundJob);
        model.addAttribute("account", accountService.getCurrentAccount());
        return "jobs/info";
    }
}
