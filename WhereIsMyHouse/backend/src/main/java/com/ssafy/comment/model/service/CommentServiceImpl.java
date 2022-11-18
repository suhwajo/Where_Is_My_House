package com.ssafy.comment.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.comment.model.dto.CommentDto;
import com.ssafy.comment.model.mapper.CommentMapper;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentDao;
	
	
	@Override
	public List<CommentDto> getComments(Integer no) throws SQLException{
		if(no!=null) return commentDao.selectByNo(no);
		else return null;
	}


	@Override
	public int write(CommentDto commentDto) throws SQLException{
		return commentDao.save(commentDto);
	}


	@Override
	public CommentDto detail(String id) throws SQLException {
		return commentDao.selectById(id);
	}


	@Override
	public int modify(String author, String id, String content) throws SQLException {
		CommentDto comment = commentDao.selectById(id);
		if(author.equals(comment.getAuthor())) {
			return commentDao.update(id, content);
		}
		return -1;
	}


	@Override
	public int delete(String id) throws SQLException {
		return commentDao.deleteById(id);
	}

}
