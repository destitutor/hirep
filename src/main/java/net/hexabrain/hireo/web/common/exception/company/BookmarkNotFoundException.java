package net.hexabrain.hireo.web.common.exception.company;

public class BookmarkNotFoundException extends RuntimeException {
    private static final String MESSAGE = "해당하는 북마크를 찾을 수 없습니다.";

    public BookmarkNotFoundException() {
        super(MESSAGE);
    }
}
