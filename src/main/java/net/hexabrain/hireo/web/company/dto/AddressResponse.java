package net.hexabrain.hireo.web.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponse {
	private CoordinateResponse coordinate;

	private String countryCode;
}
