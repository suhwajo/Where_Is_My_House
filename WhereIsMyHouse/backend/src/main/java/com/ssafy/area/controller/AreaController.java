package com.ssafy.area.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssafy.area.model.dto.*;
import com.ssafy.area.model.service.AreaService;
import com.ssafy.area.model.service.AreaServiceImpl;
import com.ssafy.area.model.service.AroundService;
import com.ssafy.area.model.service.AroundServiceImpl;
import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.service.MemberService;

@RestController
@RequestMapping("/rest/area")
@CrossOrigin("*")
public class AreaController{
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private AroundService aroundService;
	@Autowired
    private MemberService memberService;


	@GetMapping("/list")
	private ResponseEntity<?> list(@RequestParam("userId") String userId) {

		List<AreaDto> areas = null;
		try {

			areas = areaService.list(userId);
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("areas", areas);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@PostMapping("/insert")
	private ResponseEntity<?> insert(@RequestBody Map<String, String> param) {
		AreaDto areaDto = null;

		try {
			String userId = param.get("userId");
			MemberDto memberDto = (MemberDto) memberService.getMember(userId);
		
			if (memberDto != null) {

				String code = param.get("code");
				String sido = param.get("sido");
				String gugun = param.get("gugun");
				String dong = param.get("dong");

				areaDto = new AreaDto(code, memberDto.getId(), sido + " " + gugun + " " + dong);

				areaService.add(areaDto);
			}
		} catch (Exception e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

    	return new ResponseEntity<AreaDto>(areaDto, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	private ResponseEntity<?> delete(@RequestParam("code") String code, @RequestParam("userId") String userId) {
		boolean check = false;

		try {

			if (userId != null) {
				
				areaService.delete(userId, code);
				check = true;
			}
		} catch (SQLException e) {
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("check", check);

		return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
	}

	@GetMapping("/stores")
	private ResponseEntity<?> stores(@RequestParam("smallCode") String smallCode, @RequestParam("dongCode") String dongCode) throws SQLException {

		List<StoreDto> list = aroundService.selectStoreBySmallCodeAndDongCode(smallCode, dongCode);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}
	
	@GetMapping("/environments")
	private ResponseEntity<?> environments(@RequestParam("code") String code) throws SQLException {

		code = code.substring(0, 5) + "00000";
		List<EnvironmentDto> list = aroundService.selectEnvironmentByCode(code);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@GetMapping("/hospitals")
	private ResponseEntity<?> hospitals(@RequestParam("code") String code) throws SQLException {

		code = code.substring(0, 5) + "00000";
		List<HospitalDto> list = aroundService.selectHospitalByCode(code);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@GetMapping("/clinics")
	private ResponseEntity<?> clinics(@RequestParam("code") String code) throws SQLException {

		code = code.substring(0, 5) + "00000";
		List<ClinicDto> list = aroundService.selectClinicBy(code);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@GetMapping("/getBig")
	private ResponseEntity<?> getBig() throws SQLException {

		List<String> list = aroundService.selectBig();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@GetMapping("/getMiddle")
	private ResponseEntity<?> getMiddle(@RequestParam("big") String big) throws SQLException {

		List<String> list = aroundService.selectMiddleByBig(big);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", list);
        
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
	}

	@GetMapping("/getSmall")
	private ResponseEntity<?> getSmall(@RequestParam("middle") String middle) throws SQLException {

		List<Map<String, String>> map = aroundService.selectSmallByMiddle(middle);
		
        return new ResponseEntity<List<Map<String, String>>>(map,HttpStatus.OK);
	}
}
