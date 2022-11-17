package com.ssafy.util.algorithm.sort;

import com.ssafy.region.model.dto.RegionDto;

import java.util.List;

public class QuickSort {

    public static void QuickSort(RegionDto[] list, RegionComparator comparator) {
        sort(list, 0, list.length - 1, comparator);
    }

    private static void sort(RegionDto[] list, int low, int high, RegionComparator comparator) {	// left pivot
        if(low >= high) {
            return;
        }
        int pivot = partition(list, low, high, comparator);

        sort(list, low, pivot - 1, comparator);
        sort(list, pivot + 1, high, comparator);
    }

    private static int partition(RegionDto[] list, int left, int right, RegionComparator comparator) {
        int low = left;
        int high = right;
        while(low < high) {

            while(comparator.compareTo(list[high], list[left]) > 0 && low < high) {
                high--;
            }

            while(comparator.compareTo(list[low], list[left]) <= 0 && low < high) {
                low++;
            }

            swap(list, low, high);
        }
        swap(list, left, low);
        return low;
    }

    private static void swap(RegionDto[] list, int i, int j) {
        RegionDto tmp = list[i];
        list[i] = list[j];
        list[j] = tmp;
    }

}
