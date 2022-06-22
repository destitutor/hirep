package net.hexabrain.hireo.web.common.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private final int code;
    private final LocalDateTime timestamp;
    private final String message;

    public ApiException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
