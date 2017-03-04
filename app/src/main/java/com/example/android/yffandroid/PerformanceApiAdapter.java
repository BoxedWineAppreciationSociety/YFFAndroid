package com.example.android.yffandroid;

import android.support.annotation.NonNull;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by chris on 2/3/17.
 */
public class PerformanceApiAdapter {
    public static final String FRI_PERFORMANCES_URL = "https://raw.githubusercontent.com/RustComet/YFFJSON/master/fri_performances_remote.json";
    public static final String SAT_PERFORMANCES_URL = "https://raw.githubusercontent.com/RustComet/YFFJSON/master/sat_performances_remote.json";
    public static final String SUN_PERFORMANCES_URL = "https://raw.githubusercontent.com/RustComet/YFFJSON/master/sun_performances_remote.json";

    public static final String TAG = "PerformanceApiAdapter";

    public List<Map<String, String>> getPerformanceMaps(int day) {
        List<Map<String, String>> performanceMaps = new ArrayList<>();

        try {
            String performanceResponse = getApiResponse(day);
            performanceMaps = getPerformanceMapsFromResponse(performanceMaps, performanceResponse);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return performanceMaps;
    }

    private String getApiResponse(int day) throws IOException {
        URL performancesURL = PerformanceApiAdapter.buildUrl(day);
        String performancesResponse = null;
        performancesResponse = PerformanceApiAdapter.getResponseFromHttpUrl(performancesURL);
        Log.d(TAG, "Response: " + performancesResponse);
        return performancesResponse;
    }

    public static URL buildUrl(int day) {
        URL url = null;
        try {
            switch (day) {
                case Performance.FRIDAY:
                    url = new URL(FRI_PERFORMANCES_URL);
                    break;
                case Performance.SATURDAY:
                    url = new URL(SAT_PERFORMANCES_URL);
                    break;
                case Performance.SUNDAY:
                    url = new URL(SUN_PERFORMANCES_URL);
                    break;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    private List<Map<String, String>> getPerformanceMapsFromResponse(List<Map<String, String>> performanceMaps, String performanceResponse) throws JSONException {
        JSONArray performancesJSONArray = getPerformancesJSONArray(performanceResponse);
        performanceMaps = getPerformanceMapsFromJSONArray(performancesJSONArray);
        return performanceMaps;
    }

    private JSONArray getPerformancesJSONArray(String performancesResponse) throws JSONException {
        JSONArray performancesJSONArray = null;
        JSONObject responseJSON = new JSONObject(performancesResponse);
        Log.d(TAG, responseJSON.toString());
        performancesJSONArray = responseJSON.getJSONArray("performances");
        return performancesJSONArray;
    }

    private List<Map<String,String>> getPerformanceMapsFromJSONArray(JSONArray performancesJSONArray) throws JSONException {
        ArrayList<Map<String,String>> performances = new ArrayList<>();

        int performancesJSONlength = performancesJSONArray.length();
        for (int i = 0; i < performancesJSONlength; i++) {

            JSONObject performanceJSON = performancesJSONArray.optJSONObject(i);
            if (performanceJSON != null) {
                Map performance = getPerformanceMapFromJSON(performanceJSON);
                if (performance != null) {
                    performances.add(performance);
                }
            }
        }

        return performances;
    }

    @NonNull
    private Map getPerformanceMapFromJSON(JSONObject performanceJSON) {
        Map performance = new HashMap();
        for (Map.Entry<String,String> entry : performanceJSONMapping()) {
            String value = performanceJSON.optString(entry.getKey());
            performance.put(entry.getValue(), value);
        }

        if (performance.get("id") != null && performance.get("artistId") != null) {
            return performance;
        } else {
            return null;
        }
    }

    private static Set<Map.Entry<String,String>> performanceJSONMapping() {
        HashMap<String,String> attributeMappings = new HashMap<>();

        attributeMappings.put("id", "id");
        attributeMappings.put("stage", "venue");
        attributeMappings.put("artistId", "artistId");
        attributeMappings.put("time", "time");

        return attributeMappings.entrySet();
    }

    // TODO: Extract to network utils class
    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
