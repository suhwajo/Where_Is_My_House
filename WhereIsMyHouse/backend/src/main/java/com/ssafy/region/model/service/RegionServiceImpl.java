package com.ssafy.region.model.service;

import com.ssafy.region.model.dto.AmountDto;
import com.ssafy.region.model.dto.RegionDto;
import com.ssafy.region.model.mapper.RegionMapper;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionServiceImpl implements RegionService{

    private static RegionService regionService = new RegionServiceImpl();
    
    @Autowired
    private RegionMapper regionDao;


    public static RegionService getRegionService() {
        return regionService;
    }

    public List<RegionDto> dongList() throws SQLException {
        List<RegionDto> regionDtoList = regionDao.selectDong();

        return regionDtoList;
    }

    public List<RegionDto> gunguList() throws SQLException {
        List<RegionDto> regionDtoList = regionDao.selectGungu();

        return regionDtoList;
    }

    public List<RegionDto> sidoList() throws SQLException {
        List<RegionDto> regionDtoList = regionDao.selectSido();

        return regionDtoList;
    }

    public List<AmountDto> getAmounts() throws SQLException {
        return regionDao.getAmounts();
    }

    public long getHomeCount(String dongCode) throws SQLException {
        return regionDao.getHomeCount(dongCode);
    }
}
