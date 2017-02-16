package com.example.android.yffandroid;

import android.net.Uri;
import android.util.Log;

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
        Log.d(TAG, "About to get Artist Maps");
        List<Map<String, String>> artistMapsFromApi = getArtistMaps();

        return ArtistRepo.artistsFromMapList(artistMapsFromApi);
    }

    public static List<Map<String, String>> getArtistMaps() {
        URL artistsUrl = buildUrl();
        String artistsResponse = null;
        try {
            Log.d(TAG, "Hitting API");
            artistsResponse = getResponseFromHttpUrl(artistsUrl);
            Log.d(TAG, "Response: " + artistsResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // TODO: Parse Response
            // Gson good library for going straight from json string -> java objects.
            Log.d(TAG, "Parsing Response: " + artistsResponse);
            JSONObject responseJSON = new JSONObject(artistsResponse);
            Log.d(TAG, responseJSON.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, artistsResponse);
        List<Map<String, String>> artists = new ArrayList<>();
        Map artist = new HashMap();
        // TODO: Turn response into list of maps
        artist.put("id", "1D3D3DD1-3C8D-F828-0E42-D9E82B70ECF8");
        artist.put("name", "Joel Sulman");

        artists.add(artist);

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

        //Good to use the build in android library to get familiar with the inner workings. But if you want ease of use,
        //I recommend OkHttp with Jackson/GSON library so you can go straight from JSON -> List<Artist> object.
        Log.d(TAG, "Getting response");
        Log.d(TAG, "URL: " + url.toString());
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        Log.d(TAG, "Opened Connection successfully");
        try {
            Log.d(TAG, "Trying to get Input Stream");
            // TODO: Work out why we're not getting an input stream
            InputStream in = urlConnection.getInputStream();
            Log.d(TAG, "Got Input Stream");

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
