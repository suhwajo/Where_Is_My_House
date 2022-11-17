package com.ssafy.area.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RestController
@RequestMapping("/rest")
@CrossOrigin("*")
public class AreaController{
	private AreaService areaService;
	private AroundService aroundService;
	private Gson gson;

	public AreaController() {
		areaService = AreaServiceImpl.getAreaService();
		aroundService = AroundServiceImpl.getAroundService();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public String getResponseJson(String act, HttpServletRequest req) throws SQLException {
		String json = "";

		switch (act) {
			case "list":
				json = list(req);
				break;
			case "insert":
				json = insert(req);
				break;
			case "delete":
				json = delete(req);
				break;
			case "stores":
				json = stores(req);
				break;
			case "environments":
				json = environments(req);
				break;
			case "hospitals":
				json = hospitals(req);
				break;
			case "clinics":
				json = clinics(req);
				break;
			case "getBig":
				json = getBig(req);
				break;
			case "getMiddle":
				json = getMiddle(req);
				break;
			case "getSmall":
				json = getSmall(req);
				break;

		}

		return json;
	}

	private String list(HttpServletRequest request) {

		List<AreaDto> areas = null;
		try {
			areas = areaService.list(request);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return gson.toJson(areas);
	}

	private String insert(HttpServletRequest req) {
		AreaDto areaDto = null;

		try {

			HttpSession session = req.getSession();
			MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

			if (memberDto != null) {

				StringBuilder sb = new StringBuilder();
				String line;
				while((line = req.getReader().readLine()) != null)
					sb.append(line);

				JsonObject jsonObject = gson.fromJson(sb.toString(), JsonObject.class);
				String code = jsonObject.get("code").getAsString();
				String sido = jsonObject.get("sido").getAsString();
				String gugun = jsonObject.get("gugun").getAsString();
				String dong = jsonObject.get("dong").getAsString();

				areaDto = new AreaDto(code, memberDto.getId(), sido + " " + gugun + " " + dong);

				areaService.add(areaDto);
			}
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}

		return gson.toJson(areaDto);
	}
	
	private String delete(HttpServletRequest request) {
		boolean check = false;

		try {
			HttpSession session = request.getSession();
			MemberDto memberDto = (MemberDto) session.getAttribute("userInfo");

			if (memberDto != null) {
				
				String code = request.getParameter("code");

				areaService.delete(memberDto.getId(), code);
				check = true;

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("check", check);

		return gson.toJson(jsonObject);
	}

	private String stores(HttpServletRequest req) throws SQLException {

		String smallCode = req.getParameter("smallCode");
		String dongCode = req.getParameter("dongCode");
		List<StoreDto> list = aroundService.selectStoreBySmallCodeAndDongCode(smallCode, dongCode);

		return gson.toJson(list);
	}

	private String environments(HttpServletRequest req) throws SQLException {

		String code = req.getParameter("code");
		code = code.substring(0, 5) + "00000";
		List<EnvironmentDto> list = aroundService.selectEnvironmentByCode(code);

		return gson.toJson(list);
	}

	private String hospitals(HttpServletRequest req) throws SQLException {

		String code = req.getParameter("code");
		code = code.substring(0, 5) + "00000";
		List<HospitalDto> list = aroundService.selectHospitalByCode(code);

		return gson.toJson(list);
	}

	private String clinics(HttpServletRequest req) throws SQLException {

		String code = req.getParameter("code");
		code = code.substring(0, 5) + "00000";
		List<ClinicDto> list = aroundService.selectClinicBy(code);

		return gson.toJson(list);
	}

	private String getBig(HttpServletRequest req) throws SQLException {

		List<String> list = aroundService.selectBig();

		return gson.toJson(list);
	}

	private String getMiddle(HttpServletRequest req) throws SQLException {

		String big = req.getParameter("big");

		List<String> list = aroundService.selectMiddleByBig(big);

		return gson.toJson(list);
	}

	private String getSmall(HttpServletRequest req) throws SQLException {

		String middle = req.getParameter("middle");

		Map<String, String> map = aroundService.selectSmallByMiddle(middle);

		JsonArray jsonArray = new JsonArray();
		for(String key : map.keySet()) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("code", key);
			jsonObject.addProperty("name", map.get(key));

			jsonArray.add(jsonObject);
		}

		return gson.toJson(jsonArray);
	}
}
