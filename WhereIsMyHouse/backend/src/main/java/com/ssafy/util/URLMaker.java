package com.ssafy.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class URLMaker {
	
	public static String homeApiURL(String tradeType, String homeType, String code, int year, int month) {
		// url 세팅  base url + home type + trade type
		String url = "http://openapi.molit.go.kr:8081/OpenAPI_ToolInstallPackage/service/rest/RTMSOBJSvc/getRTMSDataSvc" + homeType + tradeType;
		
		// 파라미터 세팅 지역코드 + 년 월
		Map<String, String> params = new HashMap<>();
		params.put("serviceKey", Key.SERVICE_KEY);
		params.put("LAWD_CD", code);
		
		String mString = month+"";
		if(month < 10)
			mString = "0" + month;
		params.put("DEAL_YMD", year + mString);
		
		return makeURL(url, params);
	}

	private static String makeURL(String url, Map<String, String> params) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(url);
		
		if(!params.isEmpty()) {
			sb.append("?");
			
			for(Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey())
					.append("=")
					.append(entry.getValue())
					.append("&");
			}
			
			sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}

}
