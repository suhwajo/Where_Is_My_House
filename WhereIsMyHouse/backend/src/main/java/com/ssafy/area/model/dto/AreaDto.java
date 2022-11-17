package com.ssafy.area.model.dto;

import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AreaDto {
	
	private String code;
	private String user_id;
	private String address;
	
}
