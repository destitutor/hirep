package net.hexabrain.hireo.transport.mapper;

import net.hexabrain.hireo.domain.Review;
import net.hexabrain.hireo.transport.dto.ReviewDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper extends BaseMapper<ReviewDto, Review> {
}