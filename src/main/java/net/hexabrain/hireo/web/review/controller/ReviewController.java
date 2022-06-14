package net.hexabrain.hireo.web.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.review.dto.ReviewRequest;
import net.hexabrain.hireo.web.review.dto.mapper.ReviewRequestMapper;
import net.hexabrain.hireo.web.review.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewRequestMapper reviewRequestMapper;

    @PostMapping("/companies/review/{id}")
    public String review(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("review") ReviewRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors: {}", bindingResult);
            return "redirect:/companies/{id}";
        }

        reviewService.post(id, reviewRequestMapper.toEntity(dto));
        return "redirect:/companies/{id}";
    }
}
