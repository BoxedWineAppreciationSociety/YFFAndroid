package com.example.android.yffandroid;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/3/17.
 */

public class PerformanceRepo {
    private static final String TAG = "PerformanceRepo";
    private static List<Performance> mLoadedPerformancesFriday = new LinkedList<>();
    private static List<Performance> mLoadedPerformancesSaturday = new LinkedList<>();
    private static List<Performance> mLoadedPerformancesSunday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesFriday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesSaturday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesSunday = new LinkedList<>();

    public static void initLocalPerformances() {
        List<Performance> localPerformancesFri = new LinkedList<>();
        Performance joel = new Performance("abc", "1D3D3DD1-3C8D-F828-0E42-D9E82B70ECF8");
        joel.setVenue("Royal Albert Hall");
        joel.setTime(1458277200 * 1000);
        Performance candice = new Performance("def", "D7A52587-5AD0-DA40-F401-29130BB4ADC0");
        candice.setVenue("CBGB's");
        candice.setTime(1458298800 * 1000);
        Performance cousins = new Performance("ghi", "E23891A9-AEFA-EBF4-F6AC-79F3E695ED12");
        cousins.setVenue("Sydney Opera House");
        cousins.setTime(1458303300 * 1000);
        localPerformancesFri.add(joel);
        localPerformancesFri.add(candice);
        localPerformancesFri.add(cousins);
        mLocalPerformancesFriday = localPerformancesFri;

        List<Performance> localPerformancesSat = new LinkedList<>();
        Performance gorani = new Performance("jkl", "B03982D8-BA81-3B63-1066-54CCAC1D3970");
        gorani.setVenue("Gorani Hall");
        gorani.setTime(1458272200 * 1000);
        localPerformancesSat.add(gorani);
        mLocalPerformancesSaturday = localPerformancesSat;

        List<Performance> localPerformancesSun = new LinkedList<>();
        Performance bonScotts = new Performance("mno", "C1C8B5F3-6622-C48C-5162-B1234803B3A6");
        bonScotts.setVenue("Madison Square Garden");
        bonScotts.setTime(1458275200 * 1000);
        localPerformancesSun.add(bonScotts);
        mLocalPerformancesSunday = localPerformancesSun;
    }

    public static List<Performance> getPerformances() {
        if (mLoadedPerformancesFriday.size() < 1) {
            return mLocalPerformancesFriday;
        } else {
            return mLoadedPerformancesFriday;
        }
    }

    public static void loadPerformances() {
        Log.d(TAG, "Repo loading Performances");
        List<Map<String, String>> performanceMapsFri = new PerformanceApiAdapter().getPerformanceMaps(Performance.FRIDAY);
        mLoadedPerformancesFriday = performancesFromMapList(performanceMapsFri);
        List<Map<String, String>> performanceMapsSat = new PerformanceApiAdapter().getPerformanceMaps(Performance.SATURDAY);
        mLoadedPerformancesSaturday = performancesFromMapList(performanceMapsSat);
        List<Map<String, String>> performanceMapsSun = new PerformanceApiAdapter().getPerformanceMaps(Performance.SUNDAY);
        mLoadedPerformancesSunday = performancesFromMapList(performanceMapsSun);
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

    public static List<Performance> getPerformancesForDay(int day) {
        List<Performance> loaded;
        List<Performance> local;

        switch (day) {
            case Performance.FRIDAY:
                loaded = mLoadedPerformancesFriday;
                local = mLocalPerformancesFriday;
                break;
            case Performance.SATURDAY:
                loaded = mLoadedPerformancesSaturday;
                local = mLocalPerformancesSaturday;
                break;
            case Performance.SUNDAY:
                loaded = mLoadedPerformancesSunday;
                local = mLocalPerformancesSunday;
                break;
            default:
                loaded = mLoadedPerformancesFriday;
                local = mLocalPerformancesFriday;
                break;
        }

        if (loaded.size() < 1) {
            return local;
        } else {
            return loaded;
        }
    }
}
