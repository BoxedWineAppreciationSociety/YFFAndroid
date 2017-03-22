package com.yackfolkfestival.android.yffandroid;

import java.util.Arrays;

/**
 * Created by chris on 11/3/17.
 */
public class SocialLink implements Comparable<SocialLink> {
    public static final String FACEBOOK = "facebook";
    public static final String YOUTUBE = "youtube";
    public static final String TWITTER = "twitter";
    public static final String ITUNES = "itunes";
    public static final String SOUNDCLOUD = "soundcloud";
    public static final String INSTAGRAM = "instagram";
    public static final String WEBSITE = "website";

    private final int mColor;
    private final int mIcon;
    private final String mUrl;

    public static final String[] socials = {
       WEBSITE, FACEBOOK, YOUTUBE, TWITTER, ITUNES, SOUNDCLOUD, INSTAGRAM
    };

    SocialLink() {
        mColor = R.color.colorYFFOlive;
        mUrl = "http://google.com";
        mIcon =  R.drawable.ic_facebook;
    }

    SocialLink(String type, String urlString) {
        mColor = colorFor(type);
        mUrl = urlString;
        mIcon = iconFor(type);
    }

    private static int colorFor(String type) {
        int color;
        switch (type) {
            case WEBSITE:
                color = R.color.colorYFFOlive;
                break;
            case FACEBOOK:
                color = R.color.colorFacebookBlue;
                break;
            case YOUTUBE:
                color = R.color.colorYoutubeRed;
                break;
            case TWITTER:
                color = R.color.colorTwitterBlue;
                break;
            case ITUNES:
                color = R.color.colorItunesGrey;
                break;
            case SOUNDCLOUD:
                color = R.color.colorSoundcloudOrange;
                break;
            case INSTAGRAM:
                color = R.color.colorInstagramBlue;
                break;
            default:
                color = R.color.colorYFFOlive;
                break;

        }
        return color;
    }

    private static int iconFor(String type) {
        int icon;
        switch (type) {
            case WEBSITE:
                icon = R.drawable.ic_website;
                break;
            case FACEBOOK:
                icon = R.drawable.ic_facebook;
                break;
            case YOUTUBE:
                icon = R.drawable.ic_youtube;
                break;
            case TWITTER:
                icon = R.drawable.ic_twitter;
                break;
            case ITUNES:
                icon = R.drawable.ic_apple;
                break;
            case SOUNDCLOUD:
                icon = R.drawable.ic_soundcloud;
                break;
            case INSTAGRAM:
                icon = R.drawable.ic_instagram;
                break;
            default:
                icon = R.drawable.ic_website;
                break;
        }
        return icon;
    }
    

    public int getColor() {
        return mColor;
    }

    public int getIcon() {
        return mIcon;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public int compareTo(SocialLink o) {
        Integer myPosition = Arrays.asList(socials).indexOf(this);
        Integer theirPosition = Arrays.asList(socials).indexOf(o);
        return theirPosition.compareTo(myPosition);
    }
}
