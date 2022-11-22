package com.ssafy.news;

import java.net.URLEncoder;

public class NaverCrawlerMain {

	public static void main(String[] args) {
		String id = "qfw1q1eJUJnozhkzJZMv";
		String secret = "WZlZsxT4FY";
		
		try {
			NaverCrawler crawler = new NaverCrawler();
			String url = URLEncoder.encode("needjarvis","UTF-8");
			String response = crawler.search(id, secret, url);
			System.out.println(response);
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

}
