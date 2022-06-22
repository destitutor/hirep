package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class UnauthorizedException extends ApiException {
    private static final int CODE = -7;
    private static final String MESSAGE = "권한이 없습니다.";

    public UnauthorizedException() {
        super(CODE, MESSAGE);
    }
}
