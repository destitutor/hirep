package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class CompanyNotFoundException extends ApiException {
    private static final int CODE = -3;
    private static final String MESSAGE = "해당하는 회사를 찾을 수 없습니다.";

    public CompanyNotFoundException() {
        super(CODE, MESSAGE);
    }
}
