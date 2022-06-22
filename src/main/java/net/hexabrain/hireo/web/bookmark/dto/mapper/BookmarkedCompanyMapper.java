package net.hexabrain.hireo.web.bookmark.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.bookmark.dto.BookmarkedCompanyResponseDto;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.dto.mapper.CompanyInfoMapper;

@Mapper(componentModel = "spring", uses = { CompanyInfoMapper.class })
public interface BookmarkedCompanyMapper extends BaseMapper<BookmarkedCompanyResponseDto, Bookmark> {

	@Mapping(source = "createdDate", target="bookmarkedAt")
	BookmarkedCompanyResponseDto toDto(Bookmark bookmark);
}
