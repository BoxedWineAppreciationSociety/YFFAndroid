package com.yackfolkfestival.android.yffandroid;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by chris on 11/3/17.
 */

public class SocialLinkCollection {
    private final Artist mArtist;

    SocialLinkCollection(Artist artist) {
        mArtist = artist;
    }

    List<SocialLink> getSocialLinks() {
        List<SocialLink> list = new LinkedList<>();
        if (mArtist != null) {
            for(Map.Entry<String,String> entry : socialLinks()) {
                String type = entry.getKey();
                String url = entry.getValue();
                if (url != null && !url.equals("")) {
                    list.add(new SocialLink(type, url));
                }
            }

        }
        Collections.sort(list);
        return list;
    }

    private Set<Map.Entry<String, String>> socialLinks() {
        HashMap<String,String> mapping = new HashMap<>();
        mapping.put(SocialLink.WEBSITE, mArtist.getWebsiteUrl());
        mapping.put(SocialLink.FACEBOOK, mArtist.getFacebookUrl());
        mapping.put(SocialLink.YOUTUBE, mArtist.getYoutubeUrl());
        mapping.put(SocialLink.TWITTER, mArtist.getTwitterUrl());
        mapping.put(SocialLink.ITUNES, mArtist.getItunesUrl());
        mapping.put(SocialLink.SOUNDCLOUD, mArtist.getSoundcloudUrl());
        mapping.put(SocialLink.INSTAGRAM, mArtist.getInstagramUrl());

        return mapping.entrySet();
    }
}
