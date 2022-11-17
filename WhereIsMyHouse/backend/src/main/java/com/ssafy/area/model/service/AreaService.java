package com.ssafy.area.model.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ssafy.area.model.dto.AreaDto;

public interface AreaService {

	List<AreaDto> list(HttpServletRequest request) throws SQLException;

	void add( AreaDto areaDto) throws SQLException;

	void delete(String id, String code) throws SQLException;

}
