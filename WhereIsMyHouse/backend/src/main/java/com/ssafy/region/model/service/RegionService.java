package com.ssafy.region.model.service;

import com.ssafy.region.model.dto.AmountDto;
import com.ssafy.region.model.dto.RegionDto;

import java.sql.SQLException;
import java.util.List;

public interface RegionService {
    List<RegionDto> dongList() throws SQLException;
    List<RegionDto> gunguList() throws SQLException;
    List<RegionDto> sidoList() throws SQLException;
    List<AmountDto> getAmounts() throws SQLException;
    long getHomeCount(String dongCode) throws SQLException;
}
