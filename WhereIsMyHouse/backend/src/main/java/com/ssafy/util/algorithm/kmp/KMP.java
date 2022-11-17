package com.ssafy.util.algorithm.kmp;

import java.util.ArrayList;

public class KMP {
	
	// KMP 알고리즘
	public static boolean KMP(String home, String search){

		char[] homeData = home.toCharArray();
		char[] searchData = search.toCharArray();
		
		int hLength = homeData.length, sLength = searchData.length;
		// 부분일치테이블 만들기
		int pi[] = new int[sLength];
		
		for(int i=1, j=0;i<sLength;i++) {
			while(j>0 && searchData[i]!=searchData[j]) j = pi[j-1];
			
			if(searchData[i]==searchData[j]) pi[i] = ++j;
			else pi[i] = 0;
		}
		
		int cnt = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i=0,j=0; i<hLength; ++i) { 
			
			while(j>0 && homeData[i] != searchData[j]) j = pi[j-1]; 
			
			if(homeData[i] == searchData[j]) { 
				if(j == sLength-1) { 
					return true;
				}else { 
					j++;
				}
			}
		}

		return false;
	}
}
