package net.hexabrain.hireo.web.common.exception.company;

public class AccountNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 사용자를 찾을 수 없습니다.";

    public AccountNotFoundException() {
        super(MESSAGE);
    }
}
