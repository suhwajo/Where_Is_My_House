package com.ssafy.area.model.service;

import com.ssafy.area.model.dto.ClinicDto;
import com.ssafy.area.model.dto.EnvironmentDto;
import com.ssafy.area.model.dto.HospitalDto;
import com.ssafy.area.model.dto.StoreDto;
import com.ssafy.area.model.mapper.AroundMapper;

import lombok.AllArgsConstructor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class AroundServiceImpl implements AroundService {
    private static AroundService aroundService;
    private AroundMapper aroundDao;

    public static AroundService getAroundService() {
        return aroundService;
    }


    public List<StoreDto> selectStoreBySmallCodeAndDongCode(String smallCode, String dongCode) throws SQLException {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("smallCode", smallCode);
        param.put("dongCode", dongCode);
    	return aroundDao.selectStoreBySmallCodeAndDongCode(param);
    }

    public List<EnvironmentDto> selectEnvironmentByCode(String code) throws SQLException {
        return aroundDao.selectEnvironmentByCode(code);
    }

    public List<HospitalDto> selectHospitalByCode(String code) throws SQLException {
        return aroundDao.selectHospitalByCode(code);
    }

    public List<ClinicDto> selectClinicBy(String code) throws SQLException {
        return aroundDao.selectClinicBy(code);
    }

    // 재가공하기,,
    public List<Map<String, String>> selectSmallByMiddle(String middle) throws SQLException {
//    	List<Map<String, String>> param = aroundDao.selectSmallByMiddle(middle);
//    	Map<String, String> result = new HashMap<String, String>();
//    	for (Map<String, String> hashMap : param) {
//    		result.put(hashMap.get("code"), hashMap.get("name"));			
//		}
        return aroundDao.selectSmallByMiddle(middle);
    }

    public List<String> selectMiddleByBig(String big) throws SQLException {
        return aroundDao.selectMiddleByBig(big);
    }

    public List<String> selectBig() throws SQLException {
        return aroundDao.selectBig();
    }
}
