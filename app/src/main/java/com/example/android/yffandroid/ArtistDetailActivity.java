package com.example.android.yffandroid;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ArtistDetailActivity extends AppCompatActivity {
    private TextView mArtistNameTextView;
    private ImageView mArtistImageView;
    private Artist mArtist;
    private static final String TAG = "ArtistDetailActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);

        mArtistNameTextView = (TextView) findViewById(R.id.tv_artist_name);
        mArtistImageView = (ImageView) findViewById(R.id.artist_image);
        mArtist = getArtistFromExtra();

        displayArtist();

        if (getSupportFragmentManager().findFragmentById(R.id.detail_content_fragment) == null) {
            android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            ArtistAboutFragment fragment = new ArtistAboutFragment();
            fragment.setArtist(mArtist);
            transaction.replace(R.id.detail_content_fragment, fragment);
            transaction.commit();
        }

        Typeface bebasNeue = Typeface.createFromAsset(mArtistNameTextView.getContext().getAssets(), "fonts/BebasNeueRegular.otf");
        mArtistNameTextView.setTypeface(bebasNeue);
        setupActionBar();
    }

    private void displayArtist() {
        if (mArtist == null) { return; }

        setArtistText();
        setArtistImage();
    }

    private void setArtistImage() {
        Drawable drawable = mArtist.getArtistDrawable(this);

        if (drawable != null) {
            mArtistImageView.setImageDrawable(drawable);
        }
    }

    private void setArtistText() {
        mArtistNameTextView.setText(mArtist.getName());
    }


    private Artist getArtistFromExtra() {
        String artistID = getIntent().getStringExtra(ArtistsListFragment.DETAIL_KEY);
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

    private void setupActionBar() {
        ActionBar artistListActionBar = getSupportActionBar();
        artistListActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorYFFOlive)));

        artistListActionBar.setTitle("Artist");
    }
}
