package com.ssafy;

import com.ssafy.region.model.dto.RegionDto;
import com.ssafy.util.DBUtil;
import com.ssafy.util.algorithm.kmp.KMP;
import com.ssafy.util.algorithm.sort.QuickSort;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class test {

    private static DBUtil dbUtil = DBUtil.getInstance();

    public static void main(String[] args) throws SQLException {

        Executor executor = Executors.newFixedThreadPool(30);

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": hi");
        }, executor);

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(10L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ": hi");
        }, executor);

    }

}
