package net.hexabrain.hireo.api.v1.bookmark;

import lombok.RequiredArgsConstructor;
import net.hexabrain.hireo.web.bookmark.service.BookmarkService;
import net.hexabrain.hireo.web.common.security.CurrentUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BookmarkApiController {
    private final BookmarkService bookmarkService;

    @PostMapping({"/api/v1/companies/{id}/bookmarks", "/companies/{id}/bookmarks"})
    public ResponseEntity<Void> addBookmarkToCompany(
            @CurrentUser User user,
            @PathVariable("id") Long companyId) {
        Long bookmarkId = bookmarkService.addToCompany(user, companyId);
        return ResponseEntity.created(URI.create("/api/v1/companies/" + companyId + "/bookmarks/" + bookmarkId)).build();
    }

    @PostMapping({"/api/v1/jobs/{id}/bookmarks", "/jobs/{id}/bookmarks"})
    public ResponseEntity<Void> addBookmarkToJob(
            @CurrentUser User user,
            @PathVariable("id") Long jobId) {
        Long bookmarkId = bookmarkService.addToJob(user, jobId);
        return ResponseEntity.created(URI.create("/api/v1/jobs/" + jobId + "/bookmarks/" + bookmarkId)).build();
    }

    @DeleteMapping({"/api/v1/companies/{id}/bookmarks", "/companies/{id}/bookmarks"})
    public ResponseEntity<Object> deleteBookmarkOnCompany(
            @CurrentUser User user,
            @PathVariable("id") Long companyId
    ) {
        bookmarkService.deleteOnCompany(user, companyId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping({"/api/v1/jobs/{id}/bookmarks", "/jobs/{id}/bookmarks"})
    public ResponseEntity<Object> deleteBookmarkOnJob(
            @CurrentUser User user,
            @PathVariable("id") Long jobId
    ) {
        bookmarkService.deleteOnJob(user, jobId);
        return ResponseEntity.noContent().build();
    }
}
