package com.ssafy.home.parser;

import java.sql.SQLException;
import java.util.List;

import org.w3c.dom.Element;

import com.ssafy.home.model.dto.ParsingInfoDto;

public interface HomeParser {
	List<ParsingInfoDto> getOthersFromApi(String dongCode) throws SQLException;
}
