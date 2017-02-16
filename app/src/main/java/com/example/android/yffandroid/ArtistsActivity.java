package com.example.android.yffandroid;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ArtistsActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    private static final String TAG = "ArtistsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artists);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_artists);

        int orientation = LinearLayoutManager.VERTICAL;
        boolean reverseLayout = false;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, reverseLayout);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mArtistListAdapter = new ArtistListAdapter();
        mRecyclerView.setAdapter(mArtistListAdapter);

        listArtists();
    }

    private void listArtists() {
        Log.d(TAG, "Listing Artists");
        new FetchArtistsTask().execute();
    }

    public class FetchArtistsTask extends AsyncTask<Void, Void, List<Artist>> {
        @Override
        protected List<Artist> doInBackground (Void... params) {
            Log.d(TAG, "doInBackground");
            return ArtistApiAdapter.getArtists();
        }

        @Override
        protected void onPostExecute(List<Artist> artists) {
            Log.d(TAG, "Setting artist data");
            mArtistListAdapter.setArtistData(artists);
        }
    }
}
