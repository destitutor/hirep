package net.hexabrain.hireo.web.common.exception.company;

public class NoSuchJobCategoryException extends RuntimeException {
    private static final String MESSAGE = "해당하는 직종을 찾을 수 없습니다.";

    public NoSuchJobCategoryException() {
        super(MESSAGE);
    }
}
