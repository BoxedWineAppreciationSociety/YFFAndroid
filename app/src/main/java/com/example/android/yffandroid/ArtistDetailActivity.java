package com.example.android.yffandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ArtistDetailActivity extends AppCompatActivity {
    private TextView mArtistDetailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        mArtistDetailTextView = (TextView) findViewById(R.id.tv_artist_detail);
        String artistID = getIntent().getStringExtra(ArtistsActivity.DETAIL_KEY);
        Artist artist = ArtistRepo.getArtist(artistID);

        String artistName = "Not found";
        if (artist != null) {
            artistName = artist.getName();
        }

        mArtistDetailTextView.setText(artistName);
    }
}
