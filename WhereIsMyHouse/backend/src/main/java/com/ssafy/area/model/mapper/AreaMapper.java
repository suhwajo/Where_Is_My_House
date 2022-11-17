package com.ssafy.area.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ssafy.area.model.dto.AreaDto;

public interface AreaMapper {

	List<AreaDto> selectById(String id) throws SQLException;

	void save(AreaDto areaDto) throws SQLException;

//	void deleteByIdAndCode(String id, String code) throws SQLException;

	void deleteByIdAndCode(Map<String, Object> param);

}
