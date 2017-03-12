package com.example.android.yffandroid;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chris on 1/3/17.
 */

public class Performance {
    public static final int FRIDAY = 0;
    public static final int SATURDAY = 1;
    public static final int SUNDAY = 2;

    public String id;
    public String artistId;
    public String venue;
    public long time;

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

    public long getTime() {
        return time;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String formattedTime() {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("k:mm");
        return format.format(date);
    }

    public String dateAndVenue() {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("E d MMM");
        return  format.format(date) + " • " + getVenue();
    }
}
