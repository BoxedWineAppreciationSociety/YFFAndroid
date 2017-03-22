package com.yackfolkfestival.android.yffandroid;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 12/3/17.
 */
public class SummaryFragment extends android.support.v4.app.Fragment
        implements LinksAdapter.LinksAdapterOnClickHandler {
    private static final String ARTIST_KEY = "artist";
    private TextView mArtistAboutTextView;
    private TextView mLinksHeaderTextView;
    private Artist mArtist;

    public static SummaryFragment newInstance(Artist artist) {
        SummaryFragment fragment = new SummaryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARTIST_KEY, artist.getId());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.summary_fragment, container, false);
        mArtist = ArtistRepo.getArtist(getArguments().getString(ARTIST_KEY));
        mArtistAboutTextView = (TextView) rootView.findViewById(R.id.tv_artist_about);
        mLinksHeaderTextView = (TextView) rootView.findViewById(R.id.links_header);

        Typeface bebasNeue = Typeface.createFromAsset(mArtistAboutTextView.getContext().getAssets(), "fonts/BebasNeueRegular.otf");

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

    }
}
