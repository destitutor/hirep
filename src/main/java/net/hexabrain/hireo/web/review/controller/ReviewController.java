package net.hexabrain.hireo.web.review.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.review.dto.ReviewDto;
import net.hexabrain.hireo.web.review.dto.mapper.ReviewMapper;
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
    private final ReviewMapper reviewMapper;

    @PostMapping("/company/review/{id}")
    public String review(@PathVariable("id") Long id,
                         @Valid @ModelAttribute("review") ReviewDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors: {}", bindingResult);
            return "redirect:/company/{id}";
        }

        reviewService.post(id, reviewMapper.toEntity(dto));
        return "redirect:/company/{id}";
    }
}
