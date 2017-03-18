package com.example.android.yffandroid;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static android.app.PendingIntent.getActivity;

/**
 * Created by chris on 1/2/17.
 */

public class ArtistRepo extends Repo {
    private static final String TAG = "ArtistRepo";
    private static List<Artist> mLoadedArtists = new LinkedList<>();
    private static List<Artist> mLocalArtists = new LinkedList<>();

    public static void initLocalArtists(Context ctxt) {
        String jsonString = loadJSONFromAsset(ctxt, "artists_local.json");
        List<Map<String, String>> artistMaps = null;

        try {
            artistMaps = ArtistApiAdapter.getArtistMapsFromResponse(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (artistMaps != null) {
            mLocalArtists = artistsFromMapList(artistMaps);
        }
    }

    public static void loadArtists() {
        List<Map<String, String>> artistMaps = new ArtistApiAdapter().getArtistMaps();
        mLoadedArtists = artistsFromMapList(artistMaps);
    }

    public static List<Artist> getArtists() {
        if (mLoadedArtists.size() < 1) {
            return mLocalArtists;
        } else {
            return mLoadedArtists;
        }
    }

    public static Artist getArtist(String artistID) {
        Artist artist;
        if (mLoadedArtists.size() >= 1) {
            artist = getLoadedArtist(artistID);
        } else {
            artist = getLocalArtist(artistID);
        }

        if (artist != null) {
            return artist;
        } else {
            return new Artist("9B45ACB9-9656-080A-EB5A-90350C70CDA5", "COULD NOT FIND ARTIST");
        }
    }

    @Nullable
    private static Artist getLocalArtist(String artistID) {
        return findArtistInList(artistID, mLocalArtists);
    }

    @Nullable
    private static Artist getLoadedArtist(String artistID) {
        return findArtistInList(artistID, mLocalArtists);
    }

    private static Artist findArtistInList(String artistID, List<Artist> list) {
        for (int i = 0; i < list.size(); i++) {
            Artist artist = list.get(i);
            if (artist.getId().equals(artistID)) {
                return artist;
            }
        }
        return null;
    }

    public static List<Artist> artistsFromMapList(List<Map<String, String>> artistMaps) {
        List<Artist> artists = new LinkedList<>();

        for (Map artistMap : artistMaps) {
            String id = artistMap.get("id").toString();
            String name = artistMap.get("name").toString();
            String about = artistMap.get("about").toString();

            Artist newArtist = new Artist(id, name, about);
            newArtist.setYoutubeUrl(artistMap.get("youtube").toString());
            newArtist.setItunesUrl(artistMap.get("itunes").toString());
            newArtist.setSoundcloudUrl(artistMap.get("soundcloud").toString());
            newArtist.setFacebookUrl(artistMap.get("facebook").toString());
            newArtist.setTwitterUrl(artistMap.get("twitter").toString());
            newArtist.setInstagramUrl(artistMap.get("instagram").toString());
            newArtist.setWebsiteUrl(artistMap.get("website").toString());

            artists.add(newArtist);
        }

        return artists;
    }
}
