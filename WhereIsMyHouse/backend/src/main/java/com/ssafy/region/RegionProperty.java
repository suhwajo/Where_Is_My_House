package com.ssafy.region;

import com.ssafy.region.model.dto.AmountDto;
import com.ssafy.region.model.dto.RegionDto;
import com.ssafy.region.model.service.RegionService;
import com.ssafy.util.SizeConstant;
import com.ssafy.util.algorithm.kmp.KMP;
import com.ssafy.util.algorithm.sort.QuickSort;
import com.ssafy.util.algorithm.sort.RegionComparator;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
@Component
public class RegionProperty {
    private List<RegionDto> sidoList;
    private List<RegionDto> gunguList;

    @Autowired
    private RegionService regionService;

    private RegionProperty() {
        //regionService = RegionServiceImpl.getRegionService();

        Executor executor = Executors.newFixedThreadPool(30);

        CompletableFuture.runAsync(() -> {
            try {
                sidoList = regionService.sidoList();
                gunguList = regionService.gunguList();
                List<AmountDto> amounts = regionService.getAmounts();

                for (RegionDto region : sidoList)
                    setRegionDto(region, amounts);

                for (RegionDto region : gunguList)
                    setRegionDto(region, amounts);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("불러오기 종료 !! ");
        }, executor);

    }


    private void setRegionDto(RegionDto region, List<AmountDto> amounts) {

        long region_code = Long.parseLong(region.getRegion_code());
        int i = binarySearch(region_code, amounts);
        long sum = 0;
        int cnt = 0;
        long maxDiff;

        if (region.getRegion_type() == 1)
            maxDiff = 100000000;
        else if (region.getRegion_type() == 2)
            maxDiff = 100000;
        else
            maxDiff = 1;

        while (i < amounts.size() && amounts.get(i).getDongCode() - region_code < maxDiff) {
            sum += amounts.get(i).getAmount();
            i++;
            cnt++;
        }

        long avg = 0;
        if (cnt != 0)
            avg = sum / cnt;

        region.setAmountSum(sum);
        region.setDealCount(cnt);
        region.setAmountAvg(avg);
        region.setHomeCount(getHomeCount(region.getRegion_code(), region.getRegion_type()));
    }

    private long getHomeCount(String code, int type) {
        if (type == 1)
            code = code.substring(0, 2);
        else if (type == 2)
            code = code.substring(0, 5);

        try {
            return regionService.getHomeCount(code);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    private int binarySearch(long region_code, List<AmountDto> amounts) {
        int left = 0;
        int right = amounts.size() - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (region_code <= amounts.get(mid).getDongCode())
                right = mid - 1;

            else if (region_code > amounts.get(mid).getDongCode())
                left = mid + 1;
        }

        return left;
    }

    private List<RegionDto> search(String keyword, List<RegionDto> targetList) {
        List<RegionDto> list = new ArrayList<>();

        if (keyword == null || keyword.length() == 0) {
            for (RegionDto regionDto : targetList) {
                list.add(regionDto);

                if(list.size() > SizeConstant.REGION_PER_LIST)
                    break;
            }
        } else {
            for (RegionDto regionDto : targetList) {
                if (KMP.KMP(regionDto.getRegion_name(), keyword))
                    list.add(regionDto);

                if(list.size() > SizeConstant.REGION_PER_LIST)
                    break;
            }
        }
        return list;
    }

    private RegionComparator getComparator(int sortType, String sortMethod) {
        RegionComparator regionComparator = null;
        if (sortType == 1) {
            if ("ASC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o1.getHomeCount() - o2.getHomeCount());
            else if ("DESC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o2.getHomeCount() - o1.getHomeCount());
        }
        if (sortType == 2) {
            if ("ASC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o1.getDealCount() - o2.getDealCount());
            else if ("DESC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o2.getDealCount() - o1.getDealCount());
        }
        if (sortType == 3) {
            if ("ASC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o1.getAmountAvg() - o2.getAmountAvg());
            else if ("DESC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o2.getAmountAvg() - o1.getAmountAvg());
        }
        if (sortType == 4) {
            if ("ASC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o1.getAmountSum() - o2.getAmountSum());
            else if ("DESC".equals(sortMethod))
                regionComparator = ((o1, o2) -> o2.getAmountSum() - o1.getAmountSum());
        }

        return regionComparator;
    }

    public List<RegionDto> getGunguList(int sortType, String sortMethod, String keyword, int page) {

        if (sortType > 0) {
            RegionComparator regionComparator = getComparator(sortType, sortMethod);
            RegionDto[] array = gunguList.toArray(new RegionDto[gunguList.size()]);
            QuickSort.QuickSort(array, regionComparator);
            gunguList = Arrays.asList(array);
        }

        List<RegionDto> list = search(keyword, gunguList);

        return list;
    }

    public List<RegionDto> getSidoList(int sortType, String sortMethod, String keyword, int page) {

        if (sortType > 0) {
            RegionComparator regionComparator = getComparator(sortType, sortMethod);
            RegionDto[] array = sidoList.toArray(new RegionDto[sidoList.size()]);
            QuickSort.QuickSort(array, regionComparator);
            sidoList = Arrays.asList(array);
        }

        List<RegionDto> list = search(keyword, sidoList);

        return list;
    }
}
