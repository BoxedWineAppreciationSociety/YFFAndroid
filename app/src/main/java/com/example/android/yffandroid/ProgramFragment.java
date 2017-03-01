package com.example.android.yffandroid;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by chris on 28/2/17.
 */

public class ProgramFragment extends Fragment implements  ProgramAdapter.ProgramAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private ProgramAdapter mProgramAdapter;

    private static final String TAG = "ProgramFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceRepo.initLocalPerformances();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_program, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_program);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mProgramAdapter = new ProgramAdapter(this);
        mRecyclerView.setAdapter(mProgramAdapter);

        listArtists();
        return rootView;
    }

    @Override
    public void onClick(String artistId) {

    }

    private void listArtists() {
        List<Performance> performances = PerformanceRepo.getPerformances();
        mProgramAdapter.setPerformanceData(performances);
    }
}
