package net.hexabrain.hireo.web.bookmark.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.bookmark.domain.Bookmark;
import net.hexabrain.hireo.web.bookmark.dto.BookmarkDto;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface BookmarkMapper extends BaseMapper<BookmarkDto, Bookmark> {

	@Mapping(source = "company.id", target="companyId")
	@Mapping(source = "job.id", target="jobId")
	BookmarkDto toDto(Bookmark bookmark);
}
