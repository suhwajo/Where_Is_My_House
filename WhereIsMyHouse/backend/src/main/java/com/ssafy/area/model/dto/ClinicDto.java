package com.ssafy.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDto {
    long no;
    String clinicName;
    String address;
    String weekdayTime;
    String saturdayTime;
    String sundayTime;
    String holidayTime;
    String mainPhoneNumber;
    String competentCenter;
    String competentCenterPhoneNumber;
    String handicappedConvenience;
}
