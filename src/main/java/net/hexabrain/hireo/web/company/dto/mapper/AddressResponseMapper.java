package net.hexabrain.hireo.web.company.dto.mapper;

import org.mapstruct.Mapper;

import net.hexabrain.hireo.web.common.mapper.BaseMapper;
import net.hexabrain.hireo.web.company.domain.Address;
import net.hexabrain.hireo.web.company.dto.AddressResponse;

@Mapper(componentModel = "spring")
public interface AddressResponseMapper extends BaseMapper<Address, AddressResponse> {
}
