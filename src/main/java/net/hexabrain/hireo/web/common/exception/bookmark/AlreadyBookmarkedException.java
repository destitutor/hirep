package net.hexabrain.hireo.web.common.exception.bookmark;

public class AlreadyBookmarkedException extends RuntimeException {
    public static final String MESSAGE = "이미 북마크가 등록되어 있습니다.";
    
    public AlreadyBookmarkedException() {
        super(MESSAGE);
    }
}
