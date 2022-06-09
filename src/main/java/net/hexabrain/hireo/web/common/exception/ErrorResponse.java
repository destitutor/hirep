package net.hexabrain.hireo.web.common.exception;


import lombok.*;

@Data
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
public class ErrorResponse {
    private String errorMessage;
}
