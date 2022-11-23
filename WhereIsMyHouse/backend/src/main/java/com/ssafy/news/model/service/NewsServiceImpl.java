package com.ssafy.news.model.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.news.model.dto.NewsDto;
import com.ssafy.news.model.mapper.NewsMapper;

import lombok.AllArgsConstructor;
@Service
public class NewsServiceImpl implements NewsService{
	@Autowired
	private NewsMapper newsMapper;
	
	@Override
	public List<NewsDto> list(String dateHour) throws SQLException {
		
		return newsMapper.selectNews(dateHour);
	}
	@Override
	public void insertNews(List<NewsDto> newsDtos) throws SQLException {
		for (NewsDto newsDto : newsDtos) {
			newsMapper.insert(newsDto);		
		}
	}
}
