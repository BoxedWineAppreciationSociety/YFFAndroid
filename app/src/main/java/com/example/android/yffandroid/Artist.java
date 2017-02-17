package com.example.android.yffandroid;

/**
 * Created by chris on 1/2/17.
 */

public class Artist implements Comparable<Artist> {
    public String id;
    public String name;
    public String about;

    public Artist(String id, String name) {
        // Can this use the other constructor?
        this.id = id;
        this.name = name;
        this.about = "";
    }

    public Artist(String id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public String getName() {
        return name;
    }

    public String getId() { return id; }

    public String getAbout() { return about; }

    @Override
    public int compareTo(Artist otherArtist) {
        return getName().compareTo(otherArtist.getName());
    }

}