package com.ssafy.area.model.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.area.model.dto.AreaDto;
import com.ssafy.area.model.mapper.AreaMapper;
import com.ssafy.member.model.dto.MemberDto;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class AreaServiceImpl implements AreaService {
	
	private AreaMapper areaMapper;
	private static AreaService areaService;
	
	
	public static AreaService getAreaService() {
		return areaService;
	}
	

	public List<AreaDto> list(String userId) throws SQLException {
				
		if(userId != null) {
			
			return areaMapper.selectById(userId);
			
		}
		
		return null;
	}

	public void add(AreaDto areaDto) throws SQLException {
		areaMapper.save(areaDto);
	}
	
	public void delete(String id, String code) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userid", id);
		param.put("code", code);
		areaMapper.deleteByIdAndCode(param);
	}


}
