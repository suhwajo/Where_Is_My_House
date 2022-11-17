package com.ssafy.util.algorithm.sort;

import com.ssafy.region.model.dto.RegionDto;

public interface RegionComparator {
    long compareTo(RegionDto o1, RegionDto o2);
}
