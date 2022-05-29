package net.hexabrain.hireo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.service.ReviewService;
import net.hexabrain.hireo.transport.dto.ReviewDto;
import net.hexabrain.hireo.transport.mapper.ReviewMapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            return "redirect:/company/profile/{id}";
        }

        reviewService.post(id, reviewMapper.toEntity(dto));
        return "redirect:/company/profile/{id}";
    }
}
