package com.ssafy.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HospitalDto {
    long no;
    String hospitalName;
    String address;
    String phoneNumber;
}
