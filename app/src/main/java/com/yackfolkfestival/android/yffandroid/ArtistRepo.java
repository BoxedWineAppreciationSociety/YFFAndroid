package com.yackfolkfestival.android.yffandroid;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONException;

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

    public static void fetchData(fetchDataWatcher watcher) {
        new FetchArtistsTask(watcher).execute();
    }

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
            if (artist != null) {
                return artist;
            }
        }

        artist = getLocalArtist(artistID);

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
            newArtist.setImageName(artistMap.get("image_name").toString());

            artists.add(newArtist);
        }

        return artists;
    }

    public static class FetchArtistsTask extends AsyncTask<Void, Void, Void> {
        fetchDataWatcher watcher;

        public FetchArtistsTask(fetchDataWatcher watcher) {
            this.watcher = watcher;
        }

        @Override
        protected Void doInBackground (Void... params) {
            ArtistRepo.loadArtists();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Can I de-duplicate the listing logic?
            mLoadedArtists = ArtistRepo.getArtists();
            watcher.onDataFetched();
        }
    }
}
