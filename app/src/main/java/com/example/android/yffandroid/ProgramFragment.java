package com.example.android.yffandroid;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.ref.WeakReference;
import java.util.List;

import static android.R.color.black;

/**
 * Created by chris on 28/2/17.
 */

public class ProgramFragment extends android.support.v4.app.Fragment
    implements  ProgramAdapter.ProgramAdapterOnClickHandler, View.OnClickListener {
    private RecyclerView mRecyclerView;
    private ProgramAdapter mProgramAdapter;
    private Button mFriButton;
    private Button mSatButton;
    private Button mSunButton;

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

        mFriButton = (Button) rootView.findViewById(R.id.btn_friday);
        mFriButton.setOnClickListener(this);
        mSatButton = (Button) rootView.findViewById(R.id.btn_saturday);
        mSatButton.setOnClickListener(this);
        mSunButton = (Button) rootView.findViewById(R.id.btn_sunday);
        mSunButton.setOnClickListener(this);

        styleSelectedButton(mFriButton);
        listPerformances(Performance.SATURDAY);
        fetchPerformances(Performance.SUNDAY);
        return rootView;
    }

    private void styleSelectedButton(Button dayButton) {
        int selectedColor = getResources().getColor(R.color.colorAccent);
        dayButton.setTextColor(selectedColor);
    }

    @Override
    public void onClick(String artistId) {
        Context context = getActivity();
        Class destinationActivity = ArtistDetailActivity.class;
        Intent intentToStartActivity = new Intent(context, destinationActivity);
        intentToStartActivity.putExtra(ArtistsListFragment.DETAIL_KEY, artistId);
        startActivity(intentToStartActivity);
    }

    private void listPerformances(int day) {
        List<Performance> performances = PerformanceRepo.getPerformancesForDay(day);
        mProgramAdapter.setPerformanceData(performances);
    }

    private void fetchPerformances(int day) {
        Log.d(TAG, "Fetching Performances");
        int[] params = { day };
        new FetchPerformancesTask(mProgramAdapter).execute(params);
    }

    @Override
    public void onClick(View v) {
        unSelectAllButtons();
        onButtonSelected((Button)v);
    }

    private void onButtonSelected(Button v) {
        styleSelectedButton(v);
        switch (v.getId()) {
            case R.id.btn_friday:
                fetchPerformances(Performance.FRIDAY);
                break;
            case R.id.btn_saturday:
                fetchPerformances(Performance.SATURDAY);
                break;
            case R.id.btn_sunday:
                fetchPerformances(Performance.SUNDAY);
                break;

        }
    }

    private void unSelectAllButtons() {
        mFriButton.setTextColor(getResources().getColor(black));
        mSatButton.setTextColor(getResources().getColor(black));
        mSunButton.setTextColor(getResources().getColor(black));
    }

    public static class FetchPerformancesTask extends AsyncTask<int[], Void, int[]> {
        WeakReference<ProgramAdapter> programAdapterWeakReference;

        public FetchPerformancesTask(ProgramAdapter artistListAdapter) {
            this.programAdapterWeakReference = new WeakReference<>(artistListAdapter);
        }

        @Override
        protected int[] doInBackground(int[]... params) {
            Log.d(TAG, "Do in background");
            PerformanceRepo.loadPerformances();
            Log.d(TAG, "Performances loaded");

            return params[0];
        }

        @Override
        protected void onPostExecute(int[] days) {
            int day = days[0];
            // Can I de-duplicate the listing logic?
            List<Performance> performances = PerformanceRepo.getPerformancesForDay(day);
            Log.d(TAG, "onPostExecute" + String.valueOf(performances));
//            Collections.sort(performances);
            ProgramAdapter programAdapter = programAdapterWeakReference.get();
            if (programAdapter != null) {
                programAdapter.setPerformanceData(performances);
            }
        }
    }
}
