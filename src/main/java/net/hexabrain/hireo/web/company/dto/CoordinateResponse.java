package net.hexabrain.hireo.web.company.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinateResponse {
	private double latitude;

	private double longitude;
}
