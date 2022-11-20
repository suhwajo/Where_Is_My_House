package com.ssafy.home.model.service;

import com.ssafy.home.model.dto.HomeDealDto;
import com.ssafy.home.model.dto.HomeInfoDto;
import com.ssafy.home.model.dto.ParsingInfoDto;
import com.ssafy.home.model.mapper.HomeMapper;
import com.ssafy.home.parser.HomeParser;
import com.ssafy.home.parser.HomeParserImpl;

import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {
	
	private static final Logger loggers = LoggerFactory.getLogger(HomeServiceImpl.class);
	
	private static HomeServiceImpl homeServiceImpl;
	private HomeMapper homeDao;
	private HomeParser homeParser;
	
//	private HomeServiceImpl() {
//		homeParser = HomeParserImpl.getHomeParser();
//	}
	
	public static HomeService getHomeService() {
		return homeServiceImpl;
	}

	public List<HomeInfoDto> homeInfoList(String dongCode) throws SQLException {

		// 해당 동을 API 호출한 적이 없다면 API 호출을 통해 내용 추가
		
		int check = homeDao.check(dongCode);
		
		if(check == 0) {
			List<ParsingInfoDto> parsingInfoList = homeParser.getOthersFromApi(dongCode);
			
			for(ParsingInfoDto parsingInfo : parsingInfoList) {
				Long aptCode = homeDao.selectHomeInfoAptCodeByName(parsingInfo.getApartmentName());
				loggers.info(parsingInfo.getApartmentName()+" "+aptCode);
				
				// DB에 없는 아파트 정보 일 경우 새로운 info 추가
				if(aptCode==null || aptCode == -1) {
					loggers.info(parsingInfo.getDong()+" "+ parsingInfo.getDongCode()+" "+homeDao.getAptCode(parsingInfo.getDongCode().substring(0, 5)) + 1);
					parsingInfo.setDongCode(homeDao.selectDongCodeByDongName(parsingInfo.getDong(), parsingInfo.getDongCode()));
					loggers.info(parsingInfo.getDongCode());

					parsingInfo.setAptCode(homeDao.getAptCode(parsingInfo.getDongCode().substring(0, 5)) + 1);
					
					String sido = homeDao.selectSidoByDongCode(parsingInfo.getDongCode().substring(0, 2));
					String gungu = homeDao.selectGunGuByDongCode(parsingInfo.getDongCode().substring(0, 5));
					
					String address = sido + " " + gungu + " " + parsingInfo.getAddress();
					
					parsingInfo.setAddress(address);
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("aptCode", parsingInfo.getAptCode());
					param.put("dealYear", parsingInfo.getDealYear());
					param.put("dealMonth", parsingInfo.getDealMonth() + 1);
					
					parsingInfo.setNo(homeDao.getHomeDealNo(param));
					
					HomeInfoDto homeInfo = new HomeInfoDto();
					homeInfo.setDong(parsingInfo.getDong());
					homeInfo.setDongCode(homeDao.selectDongCodeByDongName(homeInfo.getDong(), parsingInfo.getDongCode()));
					homeInfo.setApartmentName(parsingInfo.getApartmentName());
					homeInfo.setType(parsingInfo.getType());
				}
			}
		}
		List<HomeInfoDto> param = homeDao.selectHomeInfoByDong(dongCode);
		for (HomeInfoDto homeInfoDto : param) {
			StringBuilder addr = new StringBuilder();
			String sidoGugun = homeDao.selectSidoGugunByDongCode(homeInfoDto.getDongCode());
			addr.append(sidoGugun).append(" ").append(homeInfoDto.getDong()).append(" ").append(homeInfoDto.getAddress());
			homeInfoDto.setAddress(addr.toString());
		}
		return param;
	}
	
	public List<HomeDealDto> homeDealList(long aptCode) throws SQLException {
		return homeDao.selectHomeDealByDong(aptCode);
	}

	public List<String> sidoList() throws SQLException {
		return homeDao.selectSido();
	}

	public List<String> gugunList(String sido) throws SQLException {
		return homeDao.selectGugun(sido);
	}

	public List<Map<String, String>> dongList(String sido,String gugun) throws SQLException {
		return homeDao.selectDong(sido,gugun);
	}
}
