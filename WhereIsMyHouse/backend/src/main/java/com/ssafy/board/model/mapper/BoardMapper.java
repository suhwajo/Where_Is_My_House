package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.board.model.dto.BoardDto;


public interface BoardMapper {
	List<BoardDto> selectList(Map<String,Object> map) throws SQLException;

	int save(BoardDto boardDto) throws SQLException;

	BoardDto selectByNo(String no) throws SQLException;

	int update(BoardDto boardDto) throws SQLException;

	int deleteByNo(String no) throws SQLException;

	void updateHit(String no) throws SQLException;

	//int start, int spl, String keyword
	Integer isEffectivePage(Map<String,Object> map) throws SQLException;
}
