package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.comment.model.dto.CommentDto;

public interface CommentService {

	List<CommentDto> getComments(Integer no) throws SQLException;

	int write(CommentDto commentDto) throws SQLException;

	CommentDto detail(String id) throws SQLException;

	int modify(String author, String id, String content) throws SQLException;

	int delete(String id) throws SQLException;

}
