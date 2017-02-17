package com.example.android.yffandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class ArtistsActivity extends AppCompatActivity implements ArtistListAdapter.ArtistAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    public static final String DETAIL_KEY = "artistID";
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

        mArtistListAdapter = new ArtistListAdapter(this);
        mRecyclerView.setAdapter(mArtistListAdapter);

        ArtistRepo.initLocalArtists();

        listArtists();
        fetchArtists();
    }

    @Override
    public void onClick(String artistId) {
        Context context = this;
        Class destinationActivity = ArtistDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationActivity);
        intentToStartActivity.putExtra(ArtistsActivity.DETAIL_KEY, artistId);
        startActivity(intentToStartActivity);
    }

    public static class FetchArtistsTask extends AsyncTask<Void, Void, Void> {
        WeakReference<ArtistListAdapter> artistListAdapterWeakReference;

        public FetchArtistsTask(ArtistListAdapter artistListAdapter) {
            this.artistListAdapterWeakReference = new WeakReference<>(artistListAdapter);
        }

        @Override
        protected Void doInBackground (Void... params) {
            ArtistRepo.loadArtists();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Can I de-duplicate the listing logic?
            List<Artist> artists = ArtistRepo.getArtists();
            Collections.sort(artists);
            ArtistListAdapter artistListAdapter = artistListAdapterWeakReference.get();
            if (artistListAdapter != null) {
                artistListAdapter.setArtistData(artists);
            }
        }
    }

    private void fetchArtists() {
        Log.d(TAG, "Listing Artists");
        new FetchArtistsTask(mArtistListAdapter).execute();
    }

    private void listArtists() {
        List<Artist> artists = ArtistRepo.getArtists();
        Collections.sort(artists);
        mArtistListAdapter.setArtistData(artists);
    }
}
