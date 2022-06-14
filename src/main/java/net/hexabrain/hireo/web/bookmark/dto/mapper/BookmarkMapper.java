package net.hexabrain.hireo.web.bookmark.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.bookmark.dto.BookmarkedCompanyResponse;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.CompanyResponseMapper;

@Mapper(componentModel = "spring", uses = { CompanyResponseMapper.class })
public interface BookmarkMapper extends BaseMapper<BookmarkedCompanyResponse, Bookmark> {

	@Mapping(source = "createdDate", target="bookmarkedAt")
	BookmarkedCompanyResponse toDto(Bookmark bookmark);
}
