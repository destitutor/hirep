package net.hexabrain.hireo.web.common.exception.company;

public class CompanyNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 회사를 찾을 수 없습니다.";

    public CompanyNotFoundException() {
        super(MESSAGE);
    }
}
