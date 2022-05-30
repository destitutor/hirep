package net.hexabrain.hireo.api.v1;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.service.BookmarkService;
import net.hexabrain.hireo.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookmark")
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkService bookmarkService;
    private final CompanyService companyService;

    @PostMapping("/company/{id}")
    public ResponseEntity<Object> addBookmark(@PathVariable("id") Long id) {
        if (!companyService.isExist(id)) {
            return new ResponseEntity<>(String.format("Company with id %d does not exist", id), HttpStatus.NOT_FOUND);
        }

        if (!bookmarkService.isExist(id)) {
            Long bookmarkId = bookmarkService.post(id);
            return new ResponseEntity<>(String.format("Bookmark with id %d has been added", bookmarkId), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(String.format("Company id %d is already bookmarked", id), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<Object> deleteBookmark(@PathVariable("id") Long id) {
        if (!companyService.isExist(id)) {
            return new ResponseEntity<>(String.format("Company id %d does not exist", id), HttpStatus.NOT_FOUND);
        }

        if (bookmarkService.isExist(id)) {
            bookmarkService.delete(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(String.format("Company id %d is not bookmarked", id), HttpStatus.BAD_REQUEST);
        }
    }
}
