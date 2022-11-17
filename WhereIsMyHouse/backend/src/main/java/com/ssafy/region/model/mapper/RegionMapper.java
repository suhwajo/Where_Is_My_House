package com.ssafy.region.model.mapper;

import com.ssafy.region.model.dto.AmountDto;
import com.ssafy.region.model.dto.RegionDto;

import java.sql.SQLException;
import java.util.List;

public interface RegionMapper {
    List<RegionDto> selectDong() throws SQLException;
    List<RegionDto> selectGungu() throws SQLException;
    List<RegionDto> selectSido() throws SQLException;
    List<AmountDto> getAmounts() throws SQLException;
    long getHomeCount(String dongCode) throws SQLException;
}
