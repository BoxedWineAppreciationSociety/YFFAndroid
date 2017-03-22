package com.example.android.yffandroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Created by chris on 1/2/17.
 */

public class Artist implements Comparable<Artist> {
    private static final String ARTIST_IMAGE_PREFIX = "artist_image_";
    private static final String ARTIST_THUMB_PREFIX = "artist_thumb_";
    private static final int PLACEHOLDER_IMAGE_ID = R.drawable.map_icon;
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


    public String imageName;

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

    public String getImageName() { return imageName; }


    public void setAbout(String about) { this.about = about; }

    public void setYoutubeUrl(String youtubeUrl) { this.youtubeUrl = youtubeUrl; }

    public void setItunesUrl(String itunesUrl) { this.itunesUrl = itunesUrl; }

    public void setSoundcloudUrl(String soundcloudUrl) { this.soundcloudUrl = soundcloudUrl; }

    public void setFacebookUrl(String facebookUrl) { this.facebookUrl = facebookUrl; }

    public void setTwitterUrl(String twitterUrl) { this.twitterUrl = twitterUrl; }

    public void setInstagramUrl(String instagramUrl) { this.instagramUrl = instagramUrl; }

    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }

    public void setImageName(String imageName) { this.imageName = imageName; }

    @Override
    public int compareTo(Artist otherArtist) {
        return getName().compareTo(otherArtist.getName());
    }

    public Drawable getArtistDrawable(Context context) {
        Resources res = context.getResources();
        String imageName = drawableName();
        if (imageName != null && !imageName.equals("")) {
            int resID = res.getIdentifier(imageName, "drawable", context.getPackageName());
            if (resID != 0) {
                return ContextCompat.getDrawable(context, resID);
            }
        }
        return getPlaceholderImage(context);
    }

    public int getArtistThumbId(Context context) {
        Resources res = context.getResources();
        String imageName = drawableThumbName();
        if (imageName != null && !imageName.equals("")) {
            int resID = res.getIdentifier(imageName, "drawable", context.getPackageName());
            if (resID != 0) {
                return resID;
            }
        }
        return R.drawable.fake_placeholder;
    }

    public Drawable getArtistThumbDrawable(Context context) {
        Resources res = context.getResources();
        String imageName = drawableThumbName();
        if (imageName != null && !imageName.equals("")) {
            int resID = res.getIdentifier(imageName, "drawable", context.getPackageName());
            if (resID != 0) {
                return ContextCompat.getDrawable(context, resID);
            }
        }
        return getPlaceholderImage(context);
    }

    private Drawable getPlaceholderImage(Context context) {
        return ContextCompat.getDrawable(context, PLACEHOLDER_IMAGE_ID);
    }

    private String drawableName() {
        String iName = getImageName();
        if (iName == null || iName.length() == 0) return "";

        return ARTIST_IMAGE_PREFIX + iName.toLowerCase();
    }

    private String drawableThumbName() {
        String iName = getImageName();
        if (iName == null || iName.length() == 0) return "";

        return ARTIST_THUMB_PREFIX + iName.toLowerCase();
    }
}