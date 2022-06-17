package net.hexabrain.hireo.web.review.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.review.domain.Review;
import net.hexabrain.hireo.web.review.dto.ReviewRequest;

@Mapper(componentModel = "spring")
public interface ReviewRequestMapper extends BaseMapper<ReviewRequest, Review> {

	@Mapping(source = "createdDate", target = "postedAt")
	ReviewRequest toDto(Review review);
}