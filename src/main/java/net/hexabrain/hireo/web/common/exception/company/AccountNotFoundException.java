package net.hexabrain.hireo.web.common.exception.company;

 import net.hexabrain.hireo.web.common.exception.ApiException;

public class AccountNotFoundException extends ApiException {
    private static final int CODE = -1;
    private static final String MESSAGE = "해당하는 사용자를 찾을 수 없습니다.";

    public AccountNotFoundException() {
        super(CODE, MESSAGE);
    }
}
