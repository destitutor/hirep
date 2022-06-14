package net.hexabrain.hireo.web.account.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.account.domain.Profile;
import net.hexabrain.hireo.web.account.dto.ProfileDto;
import net.hexabrain.hireo.web.common.mapper.BaseMapper;

@Mapper(componentModel = "spring")
public interface ProfileResponseMapper extends BaseMapper<ProfileDto, Profile> {
}
