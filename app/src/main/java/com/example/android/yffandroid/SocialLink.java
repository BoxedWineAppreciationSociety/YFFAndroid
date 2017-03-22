package com.example.android.yffandroid;

import android.graphics.Color;
import android.graphics.drawable.Drawable;

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
    private final String mUrl;
    private final String mTitle;

    public static final String[] socials = {
       WEBSITE, FACEBOOK, YOUTUBE, TWITTER, ITUNES, SOUNDCLOUD, INSTAGRAM
    };

    SocialLink() {
        mTitle = "Link";
        mColor = R.color.colorYFFOlive;
        mUrl = "http://google.com";
    }

    SocialLink(String type, String urlString) {
        mTitle = titleFor(type);
        mColor = colorFor(type);
        mUrl = urlString;
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

    // TODO: Icon instead of title
    private static String titleFor(String type) {
        String title;
        switch (type) {
            case WEBSITE:
                title = "Website";
                break;
            case FACEBOOK:
                title = "Facebook";
                break;
            case YOUTUBE:
                title = "Youtube";
                break;
            case TWITTER:
                title = "Twitter";
                break;
            case ITUNES:
                title = "iTunes";
                break;
            case SOUNDCLOUD:
                title = "Soundcloud";
                break;
            case INSTAGRAM:
                title = "Instagram";
                break;
            default:
                title = "Link";
                break;

        }
        return title;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getColor() {
        return mColor;
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
