package com.example.android.yffandroid;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/3/17.
 */

public class PerformanceRepo {
    private static final String TAG = "PerformanceRepo";
    private static List<Performance> mLoadedPerformancesFriday = new LinkedList<>();
    private static List<Performance> mLocalPerformances = new LinkedList<>();
    public static final String FRIDAY = "fri";

    public static void initLocalPerformances() {
        List<Performance> localPerformances = new LinkedList<>();
        Performance joel = new Performance("abc", "1D3D3DD1-3C8D-F828-0E42-D9E82B70ECF8");
        joel.setVenue("Royal Albert Hall");
        joel.setTime(1458277200 * 1000);
        Performance candice = new Performance("def", "D7A52587-5AD0-DA40-F401-29130BB4ADC0");
        candice.setVenue("CBGB's");
        candice.setTime(1458298800 * 1000);
        Performance cousins = new Performance("ghi", "E23891A9-AEFA-EBF4-F6AC-79F3E695ED12");
        cousins.setVenue("Sydney Opera House");
        cousins.setTime(1458303300 * 1000);
        localPerformances.add(joel);
        localPerformances.add(candice);
        localPerformances.add(cousins);
        mLocalPerformances = localPerformances;
    }

    public static List<Performance> getPerformances() {
        if (mLoadedPerformancesFriday.size() < 1) {
            return mLocalPerformances;
        } else {
            return mLoadedPerformancesFriday;
        }
    }

    public static void loadPerformances() {
        List<Map<String, String>> performanceMaps = new PerformanceApiAdapter().getPerformanceMaps(PerformanceRepo.FRIDAY);
        mLoadedPerformancesFriday = performancesFromMapList(performanceMaps);
    }

    public static Performance getPerformance(String performanceID) {
        List<Performance> performances = getPerformances();
        for (int i = 0; i < performances.size(); i++) {
            Performance performance = performances.get(i);
            if (performance.getId().equals(performanceID)) {
                return performance;
            }
        }

        Log.d(TAG, "Could not find Performance");
        return new Performance("9B45ACB9-9656-080A-EB5A-90350C70CDA5", "NOT A PERFORMANCE");
    }

    public static List<Performance> performancesFromMapList(List<Map<String, String>> performanceMaps) {
        List<Performance> performances = new LinkedList<>();

        for (Map performanceMap : performanceMaps) {
            String id = performanceMap.get("id").toString();
            String artistId = performanceMap.get("artistId").toString();
            String venue = performanceMap.get("venue").toString();
            long time =  Integer.parseInt(performanceMap.get("time").toString());

            Performance newPerformance = new Performance(id, artistId);
            newPerformance.setVenue(venue);
            newPerformance.setTime(time * 1000);
            performances.add(newPerformance);
        }

        return performances;
    }
}
