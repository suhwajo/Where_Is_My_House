package com.ssafy.news.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.news.model.dto.NewsDto;

public interface NewsMapper {

	List<NewsDto> selectNews(String dateHour) throws SQLException;

	void insert(NewsDto newsDto) throws SQLException;

}
