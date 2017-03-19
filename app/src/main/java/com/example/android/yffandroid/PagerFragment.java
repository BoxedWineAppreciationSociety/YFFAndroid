package com.example.android.yffandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.TextViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 12/3/17.
 */
public class PagerFragment extends android.support.v4.app.Fragment {
    private Artist mArtist;
    private TabLayout mTabLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.summary_pager);
        mArtist = ArtistRepo.getArtist("C1C8B5F3-6622-C48C-5162-B1234803B3A6");

        pager.setAdapter(buildAdapter());

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(pager);
        mTabLayout = tabLayout;

        setTypefaceOnAllTabs();

        return rootView;
    }

    private void setTypefaceOnAllTabs() {
        Typeface bebasNeue = Typeface.createFromAsset(getContext().getAssets(), "fonts/BebasNeueRegular.otf");
        ViewGroup vg = (ViewGroup) mTabLayout.getChildAt(0);
        int tabsCount = vg.getChildCount();
        for (int j = 0; j < tabsCount; j++) {
            ViewGroup vgTab = (ViewGroup) vg.getChildAt(j);
            int tabChildrenCount = vgTab.getChildCount();
            for (int i = 0; i < tabChildrenCount; i++) {
                View tabViewChild = vgTab.getChildAt(i);
                if (tabViewChild instanceof TextView) {
                    ((TextView) tabViewChild).setTypeface(bebasNeue);
                }
            }
        }
    }

    private PagerAdapter buildAdapter() {
        return(new AboutPagerAdapter(getActivity(), getChildFragmentManager(), mArtist));
    }

    public void setArtist(Artist artist) {
        this.mArtist = artist;
    }
}
