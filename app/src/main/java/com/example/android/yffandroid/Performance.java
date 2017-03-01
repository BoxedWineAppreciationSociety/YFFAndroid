package com.example.android.yffandroid;

/**
 * Created by chris on 1/3/17.
 */

public class Performance {
    public String id;
    public String artistId;
    public String venue;
    public String time;

    public Performance(String id, String artistId) {
        this.id = id;
        this.artistId = artistId;
    }

    public String getId() {
        return id;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getVenue() {
        return venue;
    }

    public String getTime() {
        return time;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
