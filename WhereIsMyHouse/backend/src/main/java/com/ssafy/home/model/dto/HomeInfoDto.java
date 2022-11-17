package com.ssafy.home.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeInfoDto {
	
	private long aptCode;

	private int buildYear;

	private String dong;

	private String dongCode;

	private String apartmentName;

	private String address;

	private String type;
}
