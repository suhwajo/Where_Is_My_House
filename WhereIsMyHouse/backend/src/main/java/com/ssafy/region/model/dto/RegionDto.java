package com.ssafy.region.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto {

    //1 : 시도, 2 : 시군구 3 : 동
    private int region_type;

    private String region_name;

    private String region_code;

    private long amountSum;

    private long amountAvg;

    private long homeCount;

    private long dealCount;
}
