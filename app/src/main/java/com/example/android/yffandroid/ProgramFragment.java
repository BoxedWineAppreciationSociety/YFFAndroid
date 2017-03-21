package com.example.android.yffandroid;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
    implements  ProgramAdapter.ProgramAdapterOnClickHandler, View.OnClickListener, PerformanceRepo.fetchDataWatcher {
    private RecyclerView mRecyclerView;
    private ProgramAdapter mProgramAdapter;
    private Button mFriButton;
    private Button mSatButton;
    private Button mSunButton;

    private static final String TAG = "ProgramFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PerformanceRepo.initLocalPerformances(getContext());
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
        PerformanceRepo.fetchData(this);
        setupActionBar();
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

    @Override
    public void onClick(View v) {
        unSelectAllButtons();
        onButtonSelected((Button)v);
    }

    private void onButtonSelected(Button v) {
        styleSelectedButton(v);
        switch (v.getId()) {
            case R.id.btn_friday:
                mProgramAdapter.setPerformanceData(PerformanceRepo.getPerformancesForDay(Performance.FRIDAY));
                break;
            case R.id.btn_saturday:
                mProgramAdapter.setPerformanceData(PerformanceRepo.getPerformancesForDay(Performance.SATURDAY));
                break;
            case R.id.btn_sunday:
                mProgramAdapter.setPerformanceData(PerformanceRepo.getPerformancesForDay(Performance.SUNDAY));
                break;

        }
    }

    private void unSelectAllButtons() {
        mFriButton.setTextColor(getResources().getColor(black));
        mSatButton.setTextColor(getResources().getColor(black));
        mSunButton.setTextColor(getResources().getColor(black));
    }

    @Override
    public void onDataFetched() {
        listPerformances(Performance.FRIDAY);
    }


    private void setupActionBar() {
        ActionBar programActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        programActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorYFFRed)));

        programActionBar.setTitle(R.string.program_action_title);
    }
}
