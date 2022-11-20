package com.ssafy.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
	private String id;
	private String boardId;
	private String content;
	private String author;
	private String date;
}
