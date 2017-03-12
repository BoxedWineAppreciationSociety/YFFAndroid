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
    Context context;
    Artist mArtist;

    public AboutPagerAdapter(Context context, android.support.v4.app.FragmentManager mgr, Artist artist) {
        super(mgr);
        this.context = context;
        this.mArtist = artist;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        Artist artist = ArtistRepo.getArtist("B03982D8-BA81-3B63-1066-54CCAC1D3970");
        switch(position) {
            case 0:
                return SummaryFragment.newInstance(artist);
            default:
                return new ArtistPerformancesFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "About";
            default:
                return "Performances";
        }
    }
}
