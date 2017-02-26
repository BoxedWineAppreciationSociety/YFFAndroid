package com.example.android.yffandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getFragmentManager().findFragmentById(R.id.content_fragment) == null) {
            showArtistList();
        }
    }

    private void showArtistList() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ArtistsListFragment fragment = new ArtistsListFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }
}
