package com.example.android.yffandroid;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.List;

public class ArtistsListFragment extends android.app.Fragment implements ArtistListAdapter.ArtistAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    public static final String DETAIL_KEY = "artistID";
    private static final String TAG = "ArtistsListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArtistRepo.initLocalArtists();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_artists, container, false);

        int orientation = LinearLayoutManager.VERTICAL;
        boolean reverseLayout = false;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), orientation, reverseLayout);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_artists);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mArtistListAdapter = new ArtistListAdapter(this);
        mRecyclerView.setAdapter(mArtistListAdapter);

        listArtists();
        fetchArtists();
        return rootView;
    }

    @Override
    public void onClick(String artistId) {
        Context context = getActivity();
        Class destinationActivity = ArtistDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationActivity);
        intentToStartActivity.putExtra(ArtistsListFragment.DETAIL_KEY, artistId);
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
