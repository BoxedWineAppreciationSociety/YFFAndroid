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

    public static List<Artist> getArtists() {
        List<Map<String, String>> artistMapsFromApi = null;
        artistMapsFromApi = new ArtistApiAdapter().getArtistMaps();

        return ArtistRepo.artistsFromMapList(artistMapsFromApi);
    }

    public List<Map<String, String>> getArtistMaps() {
        List<Map<String, String>> artistMaps = new ArrayList<>();

        try {
            String artistsResponse = getApiResponse();
            artistMaps = getArtistMapsFromResponse(artistMaps, artistsResponse);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }

        return artistMaps;
    }

    private List<Map<String, String>> getArtistMapsFromResponse(List<Map<String, String>> artistMaps, String artistsResponse) throws JSONException {
        JSONArray artistsJSONArray = getArtistsJSONArray(artistsResponse);
        artistMaps = getArtistMapsFromJSONArray(artistsJSONArray);
        return artistMaps;
    }

    private List<Map<String,String>> getArtistMapsFromJSONArray(JSONArray artistsJSONArray) throws JSONException {
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
    private Map getArtistMapFromJSON(JSONObject artistJSON) {
        Map artist = new HashMap();
        String artistID = artistJSON.optString("id");
        String artistName = artistJSON.optString("name");
        artist.put("id", artistID);
        artist.put("name", artistName);
        if (artist.get("id") != null && artist.get("name") != null) {
            return artist;
        } else {
            return null;
        }
    }

    private JSONArray getArtistsJSONArray(String artistsResponse) throws JSONException {
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
