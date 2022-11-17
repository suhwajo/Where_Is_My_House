package com.ssafy.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClinicDto {
    long no;
    String clinic_name;
    String address;
    String weekday_time;
    String saturday_time;
    String sunday_time;
    String holiday_time;
    String main_phone_number;
    String competent_center;
    String competent_center_phone_number;
    String handicapped_convenience;
}
