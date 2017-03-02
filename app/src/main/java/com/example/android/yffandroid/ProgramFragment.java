package com.example.android.yffandroid;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;
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

        listPerformances();
        fetchPerformances();
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

    private void listPerformances() {
        List<Performance> performances = PerformanceRepo.getPerformances();
        mProgramAdapter.setPerformanceData(performances);
    }

    private void fetchPerformances() {
        Log.d(TAG, "Fetching Performances");
        new FetchPerformancesTask(mProgramAdapter).execute();
    }

    public static class FetchPerformancesTask extends AsyncTask<Void, Void, Void> {
        WeakReference<ProgramAdapter> programAdapterWeakReference;

        public FetchPerformancesTask(ProgramAdapter artistListAdapter) {
            this.programAdapterWeakReference = new WeakReference<>(artistListAdapter);
        }

        @Override
        protected Void doInBackground (Void... params) {
            PerformanceRepo.loadPerformances();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Can I de-duplicate the listing logic?
            List<Performance> performances = PerformanceRepo.getPerformances();
//            Collections.sort(performances);
            ProgramAdapter programAdapter = programAdapterWeakReference.get();
            if (programAdapter != null) {
                programAdapter.setPerformanceData(performances);
            }
        }
    }
}
