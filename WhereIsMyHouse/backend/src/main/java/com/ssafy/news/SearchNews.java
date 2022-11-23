package com.ssafy.news;
// 네이버 검색 API 예제 - 블로그 검색
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.javassist.expr.NewArray;

import com.ssafy.news.model.dto.NewsDto;


public class SearchNews {
	static String[] news;
	static List<NewsDto> articles;
	static String[] month = {"Null","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    public static List<NewsDto> crawlNews() {
        String clientId = "qfw1q1eJUJnozhkzJZMv"; //애플리케이션 클라이언트 아이디
        String clientSecret = "WZlZsxT4FY"; //애플리케이션 클라이언트 시크릿
        articles = new ArrayList<NewsDto>();
        String text = null;
        try {
            text = URLEncoder.encode("부동산", "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL,requestHeaders);

        responseBody = responseBody.replace("\\","")
        							.replace("&apos;", "")
       								.replace("<b>", "")
       								.replace("</b>", "")
       								.replace("&quot;", "")
       								.replace("}", "")
       								.replace("&quot;", "")
       								.replace("{", "");
        
        news = responseBody.split("\t\t\t");
        for (int i = 0; i < news.length; i++) {
        	news[i] = news[i].trim();
		}
        for(int i=0;i<news.length;i++) {
        	String now = news[i];
        	if(now.contains("\"title\":")) {
        		addArticle(i);
        	}
        }
        for (NewsDto newsDto : articles) {
			System.out.println(newsDto);
		}
        return articles;
    }


    private static void addArticle(int i) {
    	int now = i;
    	String titles[] = news[now].replace("\t","").split(":");
    	String links = news[now+1].replace("\t","");
    	String pubDates = news[now+4].replace("\t","")
    								.replace(",","");
    	String title = titles[1].substring(1, titles[1].length()-2);
    	String link = links.substring(16, links.length()-2);
    	String pubDate = pubDates.substring(11, pubDates.length()-1);
    	
    	pubDate = dateForamtChange(pubDate);
    	System.out.println(pubDate);
    	articles.add(new NewsDto(title, link, pubDate));    	
	}


	private static String dateForamtChange(String pubDate) {
		String dates[] = pubDate.split(" ");
		StringBuilder sb = new StringBuilder();
		sb.append(dates[3]).append("-");
		for (int i = 0; i < 13; i++) {
			if(month[i].equals(dates[2])) {
				sb.append(i).append("-").append(dates[1]).append(" ").append(dates[4]);
				break;
			}
		}
		
		return sb.toString();
	}
	private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
    
}