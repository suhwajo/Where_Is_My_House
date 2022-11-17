package com.ssafy.home.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParsingInfoDto {
	
	private long no;

	private String dealAmount;

	private int dealYear;

	private int dealMonth;

	private int dealDay;

	private String area;

	private String floor;

	private long aptCode;

	private String tradeType;
	
	private int buildYear;

	private String dong;

	private String dongCode;

	private String apartmentName;

	private String address;

	private String type;
}
