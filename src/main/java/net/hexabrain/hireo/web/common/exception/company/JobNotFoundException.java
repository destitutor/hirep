package net.hexabrain.hireo.web.common.exception.company;

public class JobNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 구직을 찾을 수 없습니다.";

    public JobNotFoundException() {
        super(MESSAGE);
    }
}
