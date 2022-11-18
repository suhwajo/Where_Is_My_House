package com.ssafy.home.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ssafy.home.model.dto.HomeDealDto;
import com.ssafy.home.model.dto.HomeInfoDto;

public interface HomeMapper {

	List<HomeInfoDto> selectHomeInfoByDong(String dongCode) throws SQLException;
	List<HomeDealDto> selectHomeDealByDong(long aptCode) throws SQLException;
    List<String> selectSido() throws SQLException;
	List<String> selectGugun(String sido) throws SQLException;
	List<Map<String, String>> selectDong(String sido, String gugun) throws SQLException;
	int check(String dongCode) throws SQLException;
	void saveHomeDeal(HomeDealDto homeDealDto) throws SQLException;
//	long getHomeDealNo(long aptCode, int dealYear, int dealMonth) throws SQLException;
	long getHomeDealNo(Map<String, Object> param);
	void saveHomeInfo(HomeInfoDto homeInfoDto) throws SQLException;
	long getAptCode(String code) throws SQLException;
	long getDummyCode(String code) throws SQLException;
	Long selectHomeInfoAptCodeByName(String name) throws SQLException;
	String selectDongCodeByDongName(@Param("dongName") String dongName, @Param("code") String code) throws SQLException;
	String selectSidoByDongCode(String code) throws SQLException;
	String selectGunGuByDongCode(String code) throws SQLException;
	String selectSidoGugunByDongCode(String dongCode) throws SQLException;
}
