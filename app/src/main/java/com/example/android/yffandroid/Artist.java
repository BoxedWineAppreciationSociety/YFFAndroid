package com.example.android.yffandroid;

/**
 * Created by chris on 1/2/17.
 */

public class Artist implements Comparable<Artist> {
    public String id;
    public String name;
    public String about;
    public String youtubeUrl;
    public String itunesUrl;
    public String soundcloudUrl;
    public String facebookUrl;
    public String twitterUrl;
    public String instagramUrl;
    public String websiteUrl;

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

    public String getYoutubeUrl() { return youtubeUrl; }

    public String getItunesUrl() { return itunesUrl; }

    public String getSoundcloudUrl() { return soundcloudUrl; }

    public String getTwitterUrl() { return twitterUrl; }

    public String getInstagramUrl() { return instagramUrl; }

    public String getFacebookUrl() { return facebookUrl; }


    public String getWebsiteUrl() { return websiteUrl; }

    public void setAbout(String about) { this.about = about; }

    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }

    public void setItunesUrl(String itunesUrl) { this.itunesUrl = itunesUrl; }

    public void setSoundcloudUrl(String soundcloudUrl) { this.soundcloudUrl = soundcloudUrl; }

    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }

    public void setTwitterUrl(String twitterUrl) { this.twitterUrl = twitterUrl; }

    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }

    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    @Override
    public int compareTo(Artist otherArtist) {
        return getName().compareTo(otherArtist.getName());
    }

}