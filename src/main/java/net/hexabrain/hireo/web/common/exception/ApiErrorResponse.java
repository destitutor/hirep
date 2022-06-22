package net.hexabrain.hireo.web.common.exception;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class ApiErrorResponse {
	private final int code;
	private final LocalDateTime timestamp;
	private final String message;

	public ApiErrorResponse(int code, String message, LocalDateTime timestamp) {
		this.code = code;
		this.timestamp = timestamp;
		this.message = message;
	}

	public ApiErrorResponse(ApiException apiException) {
		this(apiException.getCode(), apiException.getMessage(), apiException.getTimestamp());
	}
}
