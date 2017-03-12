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

    public AboutPagerAdapter(Context context, android.support.v4.app.FragmentManager mgr) {
        super(mgr);
        this.context = context;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return(new SummaryFragment());
    }

    @Override
    public int getCount() {
        return 2;
    }
}
