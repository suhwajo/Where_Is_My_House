package com.ssafy.area.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    long storeNo;
    String storeName;
    String bigCode;
    String bigName;
    String middleCode;
    String middleName;
    String smallCode;
    String smallName;
    String sidoName;
    String gugunName;
    String dongName;
    String dongCode;
    String address;
}
