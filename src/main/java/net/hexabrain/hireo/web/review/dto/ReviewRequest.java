package net.hexabrain.hireo.web.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

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

    @NotNull
    @Length(min = 1, max = 32)
    private String name;

    private LocalDate postedAt;
}
