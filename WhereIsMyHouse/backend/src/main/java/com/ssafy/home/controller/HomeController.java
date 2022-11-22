package com.ssafy.home.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.home.model.dto.HomeDealDto;
import com.ssafy.home.model.dto.HomeInfoDto;
import com.ssafy.home.model.service.HomeService;

@RestController
@RequestMapping("/rest/home")
@CrossOrigin("*")
public class HomeController {

	@Autowired
	private HomeService homeService;


	@GetMapping("/getInfos")
	private ResponseEntity<?> getInfos(@RequestParam("code")String code) throws SQLException {
		List<HomeInfoDto> homeInfoDtoList = homeService.homeInfoList(code);
		
		return new ResponseEntity<List<HomeInfoDto>>(homeInfoDtoList,HttpStatus.OK);
	}

	@GetMapping("/getDeals")
	private ResponseEntity<?> getDeals(@RequestParam("code")long code) throws SQLException {
		List<HomeDealDto> homeDealDtoList = homeService.homeDealList(code);
		Map<String, List<HomeDealDto>> areaList = new HashMap<>();
		for(HomeDealDto homedeal:homeDealDtoList) {
			if(areaList.get(homedeal.getArea()) == null) {
				areaList.put(homedeal.getArea(),new ArrayList<HomeDealDto>());
			}
			areaList.get(homedeal.getArea()).add(homedeal);
		}
		List<Map<String, Object>> map = new ArrayList<>();
		for(Map.Entry<String, List<HomeDealDto>> elem: areaList.entrySet()) {
			Map<String,Object> mapone = new HashMap<>();
			mapone.put("area",elem.getKey());
			mapone.put("list",elem.getValue());
			map.add(mapone);
		}
		
		return new ResponseEntity<List<Map<String, Object>>>(map,HttpStatus.OK);
	}
	
	@GetMapping("/getSido")
	private ResponseEntity<?> getSido() throws SQLException {
		List<String> sidoList = homeService.sidoList();

		return new ResponseEntity<List<String>>(sidoList,HttpStatus.OK);
	}

	@GetMapping("/getGugun")
	private ResponseEntity<?> getGugun(@RequestParam("sido")String sido) throws SQLException {
		List<String> gugunList = homeService.gugunList(sido);

		return new ResponseEntity<List<String>>(gugunList,HttpStatus.OK);
	}

	@GetMapping("/getDong")
	private ResponseEntity<?> getDong(@RequestParam("sido")String sido,@RequestParam("gugun")String gugun) throws SQLException {
		List<Map<String, String>> dongMap = homeService.dongList(sido,gugun);

		return new ResponseEntity<List<Map<String, String>>>(dongMap,HttpStatus.OK);
	}
}