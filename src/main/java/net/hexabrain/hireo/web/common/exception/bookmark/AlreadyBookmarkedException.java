package net.hexabrain.hireo.web.common.exception.bookmark;

import net.hexabrain.hireo.web.common.exception.ApiException;

public class AlreadyBookmarkedException extends ApiException {
    private static final int CODE = -8;
    private static final String MESSAGE = "이미 북마크가 등록되어 있습니다.";
    
    public AlreadyBookmarkedException() {
        super(CODE, MESSAGE);
    }
}
