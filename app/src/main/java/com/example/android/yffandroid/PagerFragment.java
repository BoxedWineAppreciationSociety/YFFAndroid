package com.example.android.yffandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chris on 12/3/17.
 */
public class PagerFragment extends android.support.v4.app.Fragment {
    private Artist mArtist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.about_pager, container, false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.summary_pager);
        mArtist = ArtistRepo.getArtist("C1C8B5F3-6622-C48C-5162-B1234803B3A6");

        pager.setAdapter(buildAdapter());

        return rootView;
    }

    private PagerAdapter buildAdapter() {
        return(new AboutPagerAdapter(getActivity(), getChildFragmentManager(), mArtist));
    }

    public void setArtist(Artist artist) {
        this.mArtist = artist;
    }
}
