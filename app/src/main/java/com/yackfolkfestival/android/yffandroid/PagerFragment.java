package com.yackfolkfestival.android.yffandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 12/3/17.
 */
public class PagerFragment extends android.support.v4.app.Fragment {
    private static final String ARTIST_KEY = "artist_key";
    private Artist mArtist;
    private TabLayout mTabLayout;

    public static PagerFragment newInstance(Artist artist) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_KEY, artist.getId());
        pagerFragment.setArguments(bundle);
        return pagerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.pager_fragment, container, false);
        ViewPager pager = (ViewPager) rootView.findViewById(R.id.summary_pager);

        mArtist = ArtistRepo.getArtist(getArguments().getString(ARTIST_KEY));

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
