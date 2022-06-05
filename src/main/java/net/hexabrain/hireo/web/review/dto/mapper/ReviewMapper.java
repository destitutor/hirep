package net.hexabrain.hireo.web.review.dto.mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.review.domain.Review;
import net.hexabrain.hireo.web.review.dto.ReviewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<ReviewDto, Review> {
}