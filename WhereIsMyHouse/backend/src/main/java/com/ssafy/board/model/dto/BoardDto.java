package com.ssafy.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {
	private String no;
	private String title;
	private String content;
	private String author;
	private String date;
	private int hit;
}
