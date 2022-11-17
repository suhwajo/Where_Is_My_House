package com.ssafy.member.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

	private String id;

	private String pwd;

	private String name;

	private String email;

	private String phoneNum;

	private Boolean adminAccount;

}
