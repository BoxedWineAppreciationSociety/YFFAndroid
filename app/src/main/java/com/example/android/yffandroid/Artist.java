package com.example.android.yffandroid;

/**
 * Created by chris on 1/2/17.
 */

public class Artist implements Comparable<Artist> {
    public String id;
    public String name;

    public Artist(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() { return id; }

    @Override
    public int compareTo(Artist otherArtist) {
        return getName().compareTo(otherArtist.getName());
    }
}