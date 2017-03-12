package com.example.android.yffandroid;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 4/3/17.
 */

public class ArtistAboutFragment extends android.support.v4.app.Fragment
        implements LinksAdapter.LinksAdapterOnClickHandler {
    private TextView mArtistAboutTextView;
    private TextView mAboutHeaderTextView;
    private TextView mLinksHeaderTextView;
    private Artist mArtist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: Display properly when activity recreated
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_detail_about, container, false);
        mArtistAboutTextView = (TextView) rootView.findViewById(R.id.tv_artist_about);
        mAboutHeaderTextView = (TextView) rootView.findViewById(R.id.about_header);
        mLinksHeaderTextView = (TextView) rootView.findViewById(R.id.links_header);

        Typeface bebasNeue = Typeface.createFromAsset(mArtistAboutTextView.getContext().getAssets(), "fonts/BebasNeueRegular.otf");

        mAboutHeaderTextView.setTypeface(bebasNeue);
        mLinksHeaderTextView.setTypeface(bebasNeue);

        RecyclerView linksRV = (RecyclerView) rootView.findViewById(R.id.rv_links);linksRV.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        linksRV.setHasFixedSize(true);

        LinksAdapter linksAdapter = new LinksAdapter(this, mArtist);
        linksRV.setAdapter(linksAdapter);

        displayArtist();
        return rootView;
    }

    private void displayArtist() {
        if (mArtist == null) {
            mArtistAboutTextView.setText("Artist Detail");
            return;
        }

        mArtistAboutTextView.setText(mArtist.getAbout());
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    @Override
    public void onClick(String url) {
        if (url == null || url.equals("")) { return; }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
