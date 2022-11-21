package com.ssafy.area.model.service;

import com.ssafy.area.model.dto.ClinicDto;
import com.ssafy.area.model.dto.EnvironmentDto;
import com.ssafy.area.model.dto.HospitalDto;
import com.ssafy.area.model.dto.StoreDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface AroundService {

    List<StoreDto> selectStoreBySmallCodeAndDongCode(String smallCode, String dongCode) throws SQLException;
    List<EnvironmentDto> selectEnvironmentByCode(String code) throws SQLException;
    List<HospitalDto> selectHospitalByCode(String code) throws SQLException;
    List<ClinicDto> selectClinicBy(String code) throws SQLException;
    List<Map<String, String>> selectSmallByMiddle(String middle) throws SQLException;
    List<String> selectMiddleByBig(String big) throws SQLException;
    List<String> selectBig() throws SQLException;
}
