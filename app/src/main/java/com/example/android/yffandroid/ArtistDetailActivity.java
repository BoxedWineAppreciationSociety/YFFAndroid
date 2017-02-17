package com.example.android.yffandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ArtistDetailActivity extends AppCompatActivity {
    private TextView mArtistNameTextView;
    private TextView mArtistAboutTextView;
    private Artist mArtist;
    private static final String TAG = "ArtistDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        mArtistNameTextView = (TextView) findViewById(R.id.tv_artist_name);
        mArtistAboutTextView = (TextView) findViewById(R.id.tv_artist_about);
        mArtist = getArtistFromExtra();

        displayArtist();
    }

    private void displayArtist() {
        String artistName = "Not found";
        String artistAbout = "Not found";

        if (mArtist != null) {
            artistName = mArtist.getName();
            artistAbout = mArtist.getAbout();
        }

        mArtistNameTextView.setText(artistName);
        mArtistAboutTextView.setText(artistAbout);
    }

    private Artist getArtistFromExtra() {
        String artistID = getIntent().getStringExtra(ArtistsActivity.DETAIL_KEY);
        Artist artist = ArtistRepo.getArtist(artistID);
        return artist;
    }
}
