package com.ssafy.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvironmentDto {
    long no;
    String name;
    String industryCode;
    String industryName;
    String inspAgcy;
    String inspAgcyName;
    String checkList;
    String address;
}
