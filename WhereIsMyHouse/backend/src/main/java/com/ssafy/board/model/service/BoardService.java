package com.ssafy.board.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.board.model.dto.BoardDto;

public interface BoardService {
	int delete(String no) throws SQLException;

	int modify(BoardDto boardDto) throws SQLException;

	BoardDto detail(String no) throws SQLException;

	int write(BoardDto boardDto) throws SQLException;

	List<BoardDto> list(int page, String keyword) throws SQLException;

	void hit(String no) throws SQLException;

    List<Integer> pages(int page, String keyword) throws SQLException;
}
