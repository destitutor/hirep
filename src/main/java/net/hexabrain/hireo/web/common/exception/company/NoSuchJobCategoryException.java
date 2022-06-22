package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class NoSuchJobCategoryException extends ApiException {
    private static final int CODE = -5;
    private static final String MESSAGE = "해당하는 직종을 찾을 수 없습니다.";

    public NoSuchJobCategoryException() {
        super(CODE, MESSAGE);
    }
}
