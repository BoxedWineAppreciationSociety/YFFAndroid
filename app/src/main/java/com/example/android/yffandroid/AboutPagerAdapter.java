package com.example.android.yffandroid;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by chris on 12/3/17.
 */
public class AboutPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "About", "Performances" };

    Context context;
    Artist mArtist;

    public AboutPagerAdapter(Context context, android.support.v4.app.FragmentManager mgr, Artist artist) {
        super(mgr);
        this.context = context;
        this.mArtist = artist;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Artist artist = ArtistRepo.getArtist("ece27a39-03b8-4172-8ef0-97b3afd93a31");
        switch(position) {
            case 0:
                return SummaryFragment.newInstance(artist);
            default:
                return ArtistPerformancesFragment.newInstance(artist);
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
