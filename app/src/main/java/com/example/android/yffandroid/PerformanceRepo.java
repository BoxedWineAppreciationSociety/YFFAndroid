package com.example.android.yffandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/3/17.
 */

public class PerformanceRepo extends Repo {
    private static final String TAG = "PerformanceRepo";
    private static List<Performance> mLoadedPerformancesFriday = new LinkedList<>();
    private static List<Performance> mLoadedPerformancesSaturday = new LinkedList<>();
    private static List<Performance> mLoadedPerformancesSunday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesFriday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesSaturday = new LinkedList<>();
    private static List<Performance> mLocalPerformancesSunday = new LinkedList<>();

    public static void fetchData(fetchDataWatcher watcher) {
        new FetchPerformancesTask(watcher).execute();
    }

    public static void initLocalPerformances(Context ctxt) {
        String jsonStringFri = loadJSONFromAsset(ctxt, "fri_performances_local.json");
        String jsonStringSat = loadJSONFromAsset(ctxt, "sat_performances_local.json");
        String jsonStringSun = loadJSONFromAsset(ctxt, "sun_performances_local.json");

        try {
            List<Map<String, String>> localFri = PerformanceApiAdapter.getPerformanceMapsFromResponse(jsonStringFri);
            List<Map<String, String>> localSat = PerformanceApiAdapter.getPerformanceMapsFromResponse(jsonStringSat);
            List<Map<String, String>> localSun = PerformanceApiAdapter.getPerformanceMapsFromResponse(jsonStringSun);
            mLocalPerformancesFriday = performancesFromMapList(localFri);
            mLocalPerformancesSaturday = performancesFromMapList(localSat);
            mLocalPerformancesSunday = performancesFromMapList(localSun);
        } catch (JSONException e) {
            e.printStackTrace();
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

    public static List<Performance> getPerformancesForArtist(Artist artist) {
        if (getAllLoaded().size() < 1) {
            return filteredByArtist(getAllLocal(), artist);
        } else {
            return filteredByArtist(getAllLoaded(), artist);
        }
    }

    private static List<Performance> filteredByArtist(List<Performance> performances, final Artist artist) {
        List<Performance> filtered = new LinkedList<>();
        for(Performance perf : performances) {
            if (perf.getArtistId().equals(artist.getId())) {
                filtered.add(perf);
            }
        }
        return filtered;
    }

    public static List<Performance> getAllLoaded() {
        List<Performance> allLoaded = new LinkedList<>();
        allLoaded.addAll(mLoadedPerformancesFriday);
        allLoaded.addAll(mLoadedPerformancesSaturday);
        allLoaded.addAll(mLoadedPerformancesSunday);
        return allLoaded;
    }

    public static List<Performance> getAllLocal() {
        List<Performance> allLoaded = new LinkedList<>();
        allLoaded.addAll(mLocalPerformancesFriday);
        allLoaded.addAll(mLocalPerformancesSaturday);
        allLoaded.addAll(mLocalPerformancesSunday);
        return allLoaded;
    }


    public static class FetchPerformancesTask extends AsyncTask<Void, Void, Void> {
        fetchDataWatcher watcher;

        public FetchPerformancesTask(fetchDataWatcher watcher) {
            this.watcher = watcher;
        }

        @Override
        protected Void doInBackground (Void... params) {
            PerformanceRepo.loadPerformances();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            watcher.onDataFetched();
        }
    }
}
