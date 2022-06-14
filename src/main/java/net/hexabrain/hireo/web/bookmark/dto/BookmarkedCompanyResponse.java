package net.hexabrain.hireo.web.bookmark.dto;

import java.time.LocalDateTime;

import net.hexabrain.hireo.web.company.dto.CompanyResponse;

import lombok.Data;

@Data
public class BookmarkedCompanyResponse {
	private Long id;

	private CompanyResponse company;

	private LocalDateTime bookmarkedAt;
}
