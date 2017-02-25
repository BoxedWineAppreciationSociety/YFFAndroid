package com.example.android.yffandroid;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ArtistDetailActivity extends AppCompatActivity {
    private TextView mArtistNameTextView;
    private TextView mArtistAboutTextView;
    // TODO: RecyclerView for available buttons
    private SocialButton mFacebookButton;
    private SocialButton mInstagramButton;
    private Artist mArtist;
    private static final String TAG = "ArtistDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        mArtistNameTextView = (TextView) findViewById(R.id.tv_artist_name);
        mArtistAboutTextView = (TextView) findViewById(R.id.tv_artist_about);
        mFacebookButton = (SocialButton) findViewById(R.id.sb_facebook);
        mInstagramButton = (SocialButton) findViewById(R.id.sb_instagram);
        mArtist = getArtistFromExtra();

        displayArtist();
    }

    private void displayArtist() {
        if (mArtist == null) { return; }

        mArtistNameTextView.setText(mArtist.getName());
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

    private Artist getArtistFromExtra() {
        String artistID = getIntent().getStringExtra(ArtistsActivity.DETAIL_KEY);
        Artist artist = ArtistRepo.getArtist(artistID);
        return artist;
    }

    public void socialButtonClicked(View view) {
        SocialButton button = (SocialButton) view;
        String destinationUrl = button.getUrl();
        if (destinationUrl == null || destinationUrl.equals("")) { return; }

        Intent socialIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(destinationUrl));
        startActivity(socialIntent);
    }
}
