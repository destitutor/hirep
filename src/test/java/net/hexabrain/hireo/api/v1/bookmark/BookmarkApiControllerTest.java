package net.hexabrain.hireo.api.v1.bookmark;

import net.hexabrain.hireo.api.v1.bookmark.BookmarkApiController;
import net.hexabrain.hireo.web.bookmark.service.BookmarkService;
import net.hexabrain.hireo.web.company.service.CompanyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookmarkApiControllerTest {
    @Mock
    private BookmarkService bookmarkService;

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private BookmarkApiController controller;

    @Test
    @DisplayName("새 북마크 추가")
    void addNewBookmark() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(true);
        when(bookmarkService.isExist(companyId)).thenReturn(false);

        ResponseEntity<Object> response = controller.addBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        verify(bookmarkService).post(companyId);
    }

    @Test
    @DisplayName("이미 북마크된 회사 추가 시 에러")
    void addExistingBookmark() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(true);
        when(bookmarkService.isExist(companyId)).thenReturn(true);

        ResponseEntity<Object> response = controller.addBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

        verify(bookmarkService, never()).post(companyId);
    }

    @Test
    @DisplayName("존재하지 않는 회사에 북마크 추가 시 에러")
    void addNonExistingBookmark() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(false);

        ResponseEntity<Object> response = controller.addBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        verify(bookmarkService, never()).post(companyId);
    }

    @Test
    @DisplayName("기존 북마크 삭제")
    void deleteBookmark() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(true);
        when(bookmarkService.isExist(companyId)).thenReturn(true);

        ResponseEntity<Object> response = controller.deleteBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(bookmarkService).delete(companyId);
    }

    @Test
    @DisplayName("북마크되지 않은 회사에 북마크 삭제 시 에러")
    void deleteNonExistingBookmark() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(true);
        when(bookmarkService.isExist(companyId)).thenReturn(false);

        ResponseEntity<Object> response = controller.deleteBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        verify(bookmarkService, never()).delete(companyId);
    }

    @Test
    @DisplayName("존재하지 않는 회사에 북마크 삭제 시 에러")
    void deleteNonExistingBookmarkWhenCompanyNotExist() {
        Long companyId = 1L;

        when(companyService.isExist(companyId)).thenReturn(false);

        ResponseEntity<Object> response = controller.deleteBookmark(1L);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

        verify(bookmarkService, never()).delete(companyId);
    }

}