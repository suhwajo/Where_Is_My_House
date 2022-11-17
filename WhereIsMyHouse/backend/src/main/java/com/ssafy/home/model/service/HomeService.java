package com.ssafy.home.model.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.home.model.dto.HomeDealDto;
import com.ssafy.home.model.dto.HomeInfoDto;

public interface HomeService {

	List<HomeInfoDto> homeInfoList(String dongCode) throws SQLException;
	List<HomeDealDto> homeDealList(long aptCode) throws SQLException;

    List<String> sidoList() throws SQLException;

	List<String> gugunList(String sido) throws SQLException;

	List<Map<String, String>> dongList(String sido, String gugun) throws SQLException;
}
