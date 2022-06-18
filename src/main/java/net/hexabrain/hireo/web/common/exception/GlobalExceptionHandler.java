package net.hexabrain.hireo.web.common.exception;

import lombok.extern.slf4j.Slf4j;
import net.hexabrain.hireo.web.common.exception.bookmark.AlreadyBookmarkedException;
import net.hexabrain.hireo.web.common.exception.company.AccountNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.BookmarkNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.CompanyNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.JobNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.NoSuchJobCategoryException;
import net.hexabrain.hireo.web.common.exception.company.NoSuchJobTypeException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    @ExceptionHandler(AlreadyBookmarkedException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exception exception) {
        log.info("Bad request: {}", exception.toString());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler({
            BookmarkNotFoundException.class, CompanyNotFoundException.class, AccountNotFoundException.class,
            JobNotFoundException.class, NoSuchJobTypeException.class, NoSuchJobCategoryException.class})
    public ResponseEntity<ErrorResponse> handleNotFound(Exception exception) {
        log.info("Not found: {}", exception.toString());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse(exception.getMessage()));
    }

}
