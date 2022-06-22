package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class NoSuchJobTypeException extends ApiException {
    private static final int CODE = -6;
    private static final String MESSAGE = "해당하는 고용형태를 찾을 수 없습니다.";

    public NoSuchJobTypeException() {
        super(CODE, MESSAGE);
    }
}
