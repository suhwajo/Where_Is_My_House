package com.ssafy.news.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class NewsDto {
	private String newsTitle;
	private String newsLink;
	private String newsDate;

}
