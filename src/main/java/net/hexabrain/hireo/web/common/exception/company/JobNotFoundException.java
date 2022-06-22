package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class JobNotFoundException extends ApiException {
    private static final int CODE = -4;
    private static final String MESSAGE = "해당하는 구직을 찾을 수 없습니다.";

    public JobNotFoundException() {
        super(CODE, MESSAGE);
    }
}
