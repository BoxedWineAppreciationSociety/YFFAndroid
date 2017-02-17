package com.example.android.yffandroid;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by chris on 1/2/17.
 */

public class ArtistRepo {
    private static final String TAG = "ArtistRepo";
    private static List<Artist> mLoadedArtists = new LinkedList<>();
    private static List<Artist> mLocalArtists = new LinkedList<>();

    public static void initLocalArtists() {
        List<Artist> localArtists = new LinkedList<Artist>();
        localArtists.add(new Artist("123", "Avril Lavigne"));
        localArtists.add(new Artist("123", "Slipknot"));
        localArtists.add(new Artist("123", "Slayer"));
        mLocalArtists = localArtists;
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
        for (int i = 0; i < mLoadedArtists.size(); i++) {
            Artist artist = mLoadedArtists.get(i);
            if (artist.getId().equals(artistID)) {
                return artist;
            }
        }

        return new Artist("9B45ACB9-9656-080A-EB5A-90350C70CDA5", "NOT AN ARTIST");
    }

    public static List<Artist> artistsFromMapList(List<Map<String, String>> artistMaps) {
        List<Artist> artists = new LinkedList<>();

        for (Map artistMap : artistMaps) {
            String id = artistMap.get("id").toString();
            String name = artistMap.get("name").toString();
            String about = artistMap.get("about").toString();

            Artist newArtist = new Artist(id, name, about);
            artists.add(newArtist);
        }

        return artists;
    }
}
