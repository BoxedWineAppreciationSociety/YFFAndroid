package com.yackfolkfestival.android.yffandroid;

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
 * Created by chris on 13/2/17.
 */

public class ArtistApiAdapter {
    public static final String ARTISTS_URL = "https://raw.githubusercontent.com/RustComet/YFFJSON/master/artists_remote.json";
    public static final String TAG = "ArtistApiAdapter";

    public static URL buildUrl() {
        URL url = null;
        try {
            url = new URL(ARTISTS_URL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    ArtistApiAdapter() {};

    public List<Map<String, String>> getArtistMaps() {
        List<Map<String, String>> artistMaps = new ArrayList<>();

        try {
            String artistsResponse = getApiResponse();
            artistMaps = getArtistMapsFromResponse(artistsResponse);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return artistMaps;
    }

    public static List<Map<String, String>> getArtistMapsFromResponse(String artistsResponse) throws JSONException {
        JSONArray artistsJSONArray = getArtistsJSONArray(artistsResponse);
        List<Map<String, String>> artistMaps = getArtistMapsFromJSONArray(artistsJSONArray);
        return artistMaps;
    }

    public static List<Map<String,String>> getArtistMapsFromJSONArray(JSONArray artistsJSONArray) throws JSONException {
        ArrayList<Map<String,String>> artists = new ArrayList<>();

        int artistsJSONlength = artistsJSONArray.length();
        for (int i = 0; i < artistsJSONlength; i++) {

            JSONObject artistJSON = artistsJSONArray.optJSONObject(i);
            if (artistJSON != null) {
                Map artist = getArtistMapFromJSON(artistJSON);
                if (artist != null) {
                    artists.add(artist);
                }
            }
        }

        return artists;
    }

    @NonNull
    public static Map getArtistMapFromJSON(JSONObject artistJSON) {
        Map artist = new HashMap();
        for (Map.Entry<String,String> entry : artistJSONMapping()) {
            String value = artistJSON.optString(entry.getKey());
            artist.put(entry.getValue(), value);
        }

        if (artist.get("id") != null && artist.get("name") != null) {
            return artist;
        } else {
            return null;
        }
    }

    private static Set<Map.Entry<String,String>> artistJSONMapping() {
        HashMap<String,String> attributeMappings = new HashMap<String,String>();

        attributeMappings.put("id", "id");
        attributeMappings.put("name", "name");
        attributeMappings.put("summary", "about");
        attributeMappings.put("youtube", "youtube");
        attributeMappings.put("itunes", "itunes");
        attributeMappings.put("soundcloud", "soundcloud");
        attributeMappings.put("facebook", "facebook");
        attributeMappings.put("twitter", "twitter");
        attributeMappings.put("instagram", "instagram");
        attributeMappings.put("website", "website");
        attributeMappings.put("image_name", "image_name");

        return attributeMappings.entrySet();
    }

    public static JSONArray getArtistsJSONArray(String artistsResponse) throws JSONException {
        JSONArray artistsJSONArray = null;
        JSONObject responseJSON = new JSONObject(artistsResponse);
        Log.d(TAG, responseJSON.toString());
        artistsJSONArray = responseJSON.getJSONArray("artists");
        return artistsJSONArray;
    }

    private String getApiResponse() throws IOException {
        URL artistsUrl = ArtistApiAdapter.buildUrl();
        String artistsResponse = null;
        artistsResponse = ArtistApiAdapter.getResponseFromHttpUrl(artistsUrl);
        Log.d(TAG, "Response: " + artistsResponse);
        return artistsResponse;
    }

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
