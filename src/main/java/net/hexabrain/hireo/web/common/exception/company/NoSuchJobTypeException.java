package net.hexabrain.hireo.web.common.exception.company;

public class NoSuchJobTypeException extends RuntimeException {
    private static final String MESSAGE = "해당하는 고용형태를 찾을 수 없습니다.";

    public NoSuchJobTypeException() {
        super(MESSAGE);
    }
}
