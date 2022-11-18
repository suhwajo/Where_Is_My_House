package com.ssafy.comment.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.comment.model.dto.CommentDto;

public interface CommentMapper {

	public List<CommentDto> selectByNo(Integer no) throws SQLException;

	public int save(CommentDto commentDto) throws SQLException;

	public CommentDto selectById(String id) throws SQLException;

	public int update(@Param("id") String id, @Param("content") String content) throws SQLException;

	public int deleteById(String id) throws SQLException;

}
