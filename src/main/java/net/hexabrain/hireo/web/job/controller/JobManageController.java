package net.hexabrain.hireo.web.job.controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.hexabrain.hireo.web.common.exception.company.AccountNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.UnauthorizedException;
import net.hexabrain.hireo.web.common.security.CurrentUser;
import net.hexabrain.hireo.web.job.dto.JobPostRequestDto;
import net.hexabrain.hireo.web.job.service.JobService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/accounts/jobs")
@RequiredArgsConstructor
public class JobManageController {
	private final JobService jobService;

	@GetMapping("/post")
	public String post() {
		return "account/jobs/post";
	}

	@PostMapping("/post")
	public String post(
		@CurrentUser User user,
		@Valid JobPostRequestDto dto,
		BindingResult bindingResult
	) {
		if (bindingResult.hasErrors()) {
			log.info("bindingResult has errors: {}", bindingResult);
			return "account/jobs/post";
		}

		if (dto.getMin() > dto.getMax()) {
			bindingResult.rejectValue("min", "jobs.salary.error");
			return "account/jobs/post";
		}

		jobService.post(user, dto);
		return "account/jobs/post";
	}
}
