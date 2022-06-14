package net.hexabrain.hireo.api.v1.bookmark;

import net.hexabrain.hireo.web.bookmark.service.BookmarkService;
import net.hexabrain.hireo.web.common.exception.bookmark.AlreadyBookmarkedException;
import net.hexabrain.hireo.web.common.exception.company.BookmarkNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.CompanyNotFoundException;
import net.hexabrain.hireo.web.common.exception.company.JobNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser(username = "test")
class BookmarkApiControllerTest {
    private MockMvc mockMvc;

    @MockBean
    private BookmarkService bookmarkService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("회사에 새 북마크 추가")
    void addBookmarkToCompany() throws Exception {
        Long companyId = 1L;

        when(bookmarkService.addToCompany(any(), eq(companyId))).thenReturn(companyId);

        mockMvc.perform(post(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                .andExpect(status().isCreated());

        verify(bookmarkService).addToCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("이미 북마크된 회사 추가 시 에러")
    void addBookmarkToCompany_alreadyBookmarked() throws Exception {
        Long companyId = 1L;

        when(bookmarkService.addToCompany(any(), eq(companyId))).thenThrow(new AlreadyBookmarkedException());

        mockMvc.perform(post(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                        .andExpect(status().isBadRequest());

        verify(bookmarkService).addToCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("존재하지 않는 회사에 북마크 추가 시 에러")
    void addBookmarkToCompany_notFound() throws Exception {
        Long companyId = 1L;

        when(bookmarkService.addToCompany(any(), eq(companyId))).thenThrow(new BookmarkNotFoundException());

        mockMvc.perform(post(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                        .andExpect(status().isNotFound());

        verify(bookmarkService).addToCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("구직에 새 북마크 추가")
    void addBookmarkToJob() throws Exception {
        Long jobId = 1L;

        when(bookmarkService.addToJob(any(), eq(jobId))).thenReturn(jobId);

        mockMvc.perform(post(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                .andExpect(status().isCreated());

        verify(bookmarkService).addToJob(any(), eq(jobId));
    }

    @Test
    @DisplayName("이미 북마크된 구직 추가 시 에러")
    void addBookmarkToJob_alreadyBookmarked() throws Exception {
        Long jobId = 1L;

        when(bookmarkService.addToJob(any(), eq(jobId))).thenThrow(new AlreadyBookmarkedException());

        mockMvc.perform(post(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                        .andExpect(status().isBadRequest());

        verify(bookmarkService).addToJob(any(), eq(jobId));
    }

    @Test
    @DisplayName("존재하지 않는 구직에 북마크 추가 시 에러")
    void addBookmarkToJob_notFound() throws Exception {
        Long jobId = 1L;

        when(bookmarkService.addToJob(any(), eq(jobId))).thenThrow(new BookmarkNotFoundException());

        mockMvc.perform(post(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                        .andExpect(status().isNotFound());

        verify(bookmarkService).addToJob(any(), eq(jobId));
    }

    @Test
    @DisplayName("회사 기존 북마크 삭제")
    void deleteBookmarkFromCompany() throws Exception {
        Long companyId = 1L;

        doNothing().when(bookmarkService).deleteOnCompany(any(), eq(companyId));

        mockMvc.perform(delete(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                .andExpect(status().isNoContent());

        verify(bookmarkService).deleteOnCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("구직 기존 북마크 삭제")
    void deleteBookmarkFromJob() throws Exception {
        Long jobId = 1L;

        doNothing().when(bookmarkService).deleteOnJob(any(), eq(jobId));

        mockMvc.perform(delete(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                .andExpect(status().isNoContent());

        verify(bookmarkService).deleteOnJob(any(), eq(jobId));
    }

    @Test
    @DisplayName("북마크되지 않은 회사에 북마크 삭제 시 에러")
    void deleteBookmarkFromCompany_notBookmarked() throws Exception {
        Long companyId = 1L;

        doThrow(new BookmarkNotFoundException()).when(bookmarkService).deleteOnCompany(any(), eq(companyId));

        mockMvc.perform(delete(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                        .andExpect(status().isNotFound());

        verify(bookmarkService).deleteOnCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("존재하지 않는 회사에 북마크 삭제 시 에러")
    void deleteBookmarkFromCompany_notFound() throws Exception {
        Long companyId = 1L;

        doThrow(new CompanyNotFoundException()).when(bookmarkService).deleteOnCompany(any(), eq(companyId));

        mockMvc.perform(delete(String.format("/api/v1/companies/%d/bookmarks", companyId)))
                .andExpect(status().isNotFound());

        verify(bookmarkService).deleteOnCompany(any(), eq(companyId));
    }

    @Test
    @DisplayName("북마크되지 않은 구직에 북마크 삭제 시 에러")
    void deleteBookmarkFromJob_notBookmarked() throws Exception {
        Long jobId = 1L;

        doThrow(new BookmarkNotFoundException()).when(bookmarkService).deleteOnJob(any(), eq(jobId));

        mockMvc.perform(delete(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                        .andExpect(status().isNotFound());

        verify(bookmarkService).deleteOnJob(any(), eq(jobId));
    }

    @Test
    @DisplayName("존재하지 않는 구직에 북마크 삭제 시 에러")
    void deleteBookmarkFromJob_notFound() throws Exception {
        Long jobId = 1L;

        doThrow(new JobNotFoundException()).when(bookmarkService).deleteOnJob(any(), eq(jobId));

        mockMvc.perform(delete(String.format("/api/v1/jobs/%d/bookmarks", jobId)))
                .andExpect(status().isNotFound());

        verify(bookmarkService).deleteOnJob(any(), eq(jobId));
    }

}