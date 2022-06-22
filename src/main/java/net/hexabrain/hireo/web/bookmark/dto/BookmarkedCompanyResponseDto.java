package net.hexabrain.hireo.web.bookmark.dto;

import java.time.LocalDateTime;

import net.hexabrain.hireo.web.company.dto.CompanyInfoResponse;

import lombok.Data;

@Data
public class BookmarkedCompanyResponseDto {
	private Long id;

	private CompanyInfoResponse company;

	private LocalDateTime bookmarkedAt;
}
