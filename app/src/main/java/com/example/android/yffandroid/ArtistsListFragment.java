package com.example.android.yffandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

public class ArtistsListFragment extends Fragment
        implements ArtistListAdapter.ArtistAdapterOnClickHandler, ArtistRepo.fetchDataWatcher {
    private RecyclerView mRecyclerView;
    private ArtistListAdapter mArtistListAdapter;

    public static final String DETAIL_KEY = "artistID";
    private static final String TAG = "ArtistsListFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_artists_list, container, false);

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
        setActionBarColor();

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

    @Override
    public void onDataFetched() {
        listArtists();
    }

    private void fetchArtists() {
        Log.d(TAG, "Listing Artists");
        ArtistRepo.fetchData(this);
    }

    private void listArtists() {
        List<Artist> artists = ArtistRepo.getArtists();
        Collections.sort(artists);
        mArtistListAdapter.setArtistData(artists);
    }

    private void setActionBarColor() {
        ActionBar artistsActivityActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        artistsActivityActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorYFFOlive)));

        artistsActivityActionBar.setTitle("Artists");
    }
}
