package com.example.android.yffandroid;

import android.net.Uri;
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
import java.util.Dictionary;
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

    ArtistApiAdapter() {
        return;
    }

    public static List<Artist> getArtists() {
        List<Map<String, String>> artistMapsFromApi = null;
        artistMapsFromApi = new ArtistApiAdapter().getArtistMaps();

        return ArtistRepo.artistsFromMapList(artistMapsFromApi);
    }

    public List<Map<String, String>> getArtistMaps() {
        List<Map<String, String>> artists = new ArrayList<>();

        try {
            URL artistsUrl = ArtistApiAdapter.buildUrl();
            String artistsResponse = null;
            artistsResponse = ArtistApiAdapter.getResponseFromHttpUrl(artistsUrl);
            Log.d(TAG, "Response: " + artistsResponse);

            JSONArray artistsJSONArray = null;

            JSONObject responseJSON = new JSONObject(artistsResponse);
            Log.d(TAG, responseJSON.toString());
            artistsJSONArray = responseJSON.getJSONArray("artists");

            Log.d(TAG, artistsResponse);

            if (artistsJSONArray != null) {
                int artistsJSONlength = artistsJSONArray.length();
                for (int i = 0; i < artistsJSONlength; i++) {
                    JSONObject artistJSON = null;
                    try {
                        artistJSON = artistsJSONArray.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (artistJSON != null) {
                        Map artist = new HashMap();
                        String artistID = artistJSON.getString("id");
                        String artistName = artistJSON.getString("name");
                        artist.put("id", artistID);
                        artist.put("name", artistName);
                        artists.add(artist);
                    }

                }
            } else {
                Log.d(TAG, "Null artistsJSONArray");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return artists;
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
