package com.ssafy.news.model.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.news.model.dto.NewsDto;

public interface NewsService {

	List<NewsDto> list(String userId) throws SQLException;

	void insertNews(List<NewsDto> newsDtos) throws SQLException;

}
