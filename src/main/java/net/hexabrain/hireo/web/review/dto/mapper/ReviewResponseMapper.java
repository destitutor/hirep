package net.hexabrain.hireo.web.review.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.review.domain.Review;
import net.hexabrain.hireo.web.review.dto.ReviewResponse;

@Mapper(componentModel = "spring")
public interface ReviewResponseMapper extends BaseMapper<ReviewResponse, Review> {

	@Mapping(source = "author.profile.name", target = "name")
	@Mapping(source = "createdDate", target = "postedAt")
	ReviewResponse toDto(Review review);
}