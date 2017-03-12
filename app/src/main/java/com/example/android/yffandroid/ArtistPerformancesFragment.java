package com.example.android.yffandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chris on 12/3/17.
 */
public class ArtistPerformancesFragment extends android.support.v4.app.Fragment {
    RecyclerView mRecyclerView;
    ArtistPerformancesAdapter mPerformancesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.artist_performances, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_performances);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mPerformancesAdapter = new ArtistPerformancesAdapter(PerformanceRepo.getPerformancesForDay(Performance.FRIDAY));
        mRecyclerView.setAdapter(mPerformancesAdapter);

        listPerformances();
        fetchPerformances();

        return rootView;
    }

    private void fetchPerformances() {

    }

    private void listPerformances() {
    }
}
