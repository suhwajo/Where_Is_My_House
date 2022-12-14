package com.ssafy.news.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.news.SearchNews;
import com.ssafy.news.model.dto.NewsDto;
import com.ssafy.news.model.service.NewsService;

@RestController
@RequestMapping("/rest/news")
@CrossOrigin("*")
public class NewsController{
	
	@Autowired
	private NewsService newsService;
	private SearchNews searchNews = new SearchNews();
	
	@GetMapping("/list")
	private ResponseEntity<?> list() {
		
		List<NewsDto> news = null;
		try {
			String date = LocalDate.now().toString();
			StringBuilder timeSb = new StringBuilder();
			timeSb.append(Integer.toString(LocalTime.now().getHour()));
			String time=null;
			System.out.println(timeSb.toString());
			if(timeSb.toString().length()==1) {
				time = "0"+timeSb.toString();
				System.out.println(time);
			} else {
				time = timeSb.toString();
			}
			news = newsService.list(date+" "+time);
			if(news == null || news.size()==0 || news.size()!=15) {
				List<NewsDto> crawling = SearchNews.crawlNews();
				newsService.insertNews(crawling);
				news = newsService.list(date+" "+time);
			}
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("news", news);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
}
