package net.hexabrain.hireo.web.company.dto;

import lombok.Data;

@Data
public class CompanyResponse {
	private Long id;

	private String name;

	private String description;

	private AddressResponse address;

	private boolean isVerified;
}
