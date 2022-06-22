package net.hexabrain.hireo.web.common.exception.company;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class BookmarkNotFoundException extends ApiException {
    private static final int CODE = -2;
    private static final String MESSAGE = "해당하는 북마크를 찾을 수 없습니다.";

    public BookmarkNotFoundException() {
        super(CODE, MESSAGE);
    }
}
