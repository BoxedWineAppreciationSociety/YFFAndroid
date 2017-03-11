package com.example.android.yffandroid;

import android.app.Fragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 4/3/17.
 */

public class ArtistAboutFragment extends Fragment {
    private TextView mArtistAboutTextView;
    private TextView mAboutHeaderTextView;
    private TextView mLinksHeaderTextView;
    // TODO: RecyclerView for available buttons
    private SocialButton mFacebookButton;
    private SocialButton mInstagramButton;
    private Artist mArtist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_detail_about, container, false);
        mArtistAboutTextView = (TextView) rootView.findViewById(R.id.tv_artist_about);
        mAboutHeaderTextView = (TextView) rootView.findViewById(R.id.about_header);
        mLinksHeaderTextView = (TextView) rootView.findViewById(R.id.links_header);
        mFacebookButton = (SocialButton) rootView.findViewById(R.id.sb_facebook);
        mInstagramButton = (SocialButton) rootView.findViewById(R.id.sb_instagram);

        Typeface bebasNeue = Typeface.createFromAsset(mArtistAboutTextView.getContext().getAssets(), "fonts/BebasNeueRegular.otf");

        mAboutHeaderTextView.setTypeface(bebasNeue);
        mLinksHeaderTextView.setTypeface(bebasNeue);

        displayArtist();
        return rootView;
    }

    private void displayArtist() {
        if (mArtist == null) {
            mArtistAboutTextView.setText("Artist Detail");
            return;
        }

        mArtistAboutTextView.setText(mArtist.getAbout());

        displayFacebookButton();
        displayInstagramButton();
    }


    private void displayFacebookButton() {
        if(mArtist.getFacebookUrl() == null || mArtist.getFacebookUrl().equals("")) { return; }

        mFacebookButton.setText(R.string.facebookButtonText);
        mFacebookButton.setUrl(mArtist.getFacebookUrl());
        mFacebookButton.setVisibility(View.VISIBLE);
    }

    private void displayInstagramButton() {
        if(mArtist.getInstagramUrl() == null || mArtist.getInstagramUrl().equals("")) { return; }

        mInstagramButton.setText(R.string.instagramButtonText);
        mInstagramButton.setUrl(mArtist.getInstagramUrl());
        mInstagramButton.setVisibility(View.VISIBLE);
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }
}
