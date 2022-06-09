package net.hexabrain.hireo.web.job.controller;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.web.company.service.CompanyService;
import net.hexabrain.hireo.web.job.domain.Job;
import net.hexabrain.hireo.web.job.dto.SearchRequest;
import net.hexabrain.hireo.web.job.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobBrowseController {
    private final CompanyService companyService;
    private final JobService jobService;

    @GetMapping("/search")
    public String list(Model model, SearchRequest searchRequest,
                       @RequestParam(defaultValue = "1") int page) {
        model.addAttribute("results", jobService.search(searchRequest, page));
        return "jobs/search";
    }

    @GetMapping("/{id}")
    public String job(Model model, @PathVariable("id") Long id) {
        Job foundJob = jobService.findOne(id);
        model.addAttribute("company", foundJob.getCompany());
        model.addAttribute("job", foundJob);
        return "jobs/info";
    }
}
