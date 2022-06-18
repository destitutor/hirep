package net.hexabrain.hireo.web.account.dto;

import java.util.List;

import net.hexabrain.hireo.web.account.domain.AccountType;
import net.hexabrain.hireo.web.bookmark.dto.BookmarkDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountDto {
	private Long id;

	private String email;

	private AccountType type;

	private ProfileDto profile;

	private List<BookmarkDto> bookmarks;

	public boolean isCompanyBookmarked(Long companyId) {
		return bookmarks.stream()
			.filter(bookmark -> bookmark.getCompanyId() != null)
			.anyMatch(bookmark -> bookmark.getCompanyId().equals(companyId));
	}

	public boolean isJobBookmarked(Long jobId) {
		return bookmarks.stream()
			.filter(bookmark -> bookmark.getJobId() != null)
			.anyMatch(bookmark -> bookmark.getJobId().equals(jobId));
	}
}
