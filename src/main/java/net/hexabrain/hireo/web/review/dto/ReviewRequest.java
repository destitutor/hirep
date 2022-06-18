package net.hexabrain.hireo.web.review.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ReviewRequest {
    @NotEmpty
    @Length(min = 1, max = 32)
    private String title;

    @Range(min = 1, max = 5)
    private int rating;

    @NotEmpty
    @Length(min = 10, max = 150)
    private String content;

    private LocalDate postedAt;
}
