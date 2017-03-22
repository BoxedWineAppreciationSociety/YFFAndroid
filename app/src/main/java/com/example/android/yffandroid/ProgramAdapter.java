package com.example.android.yffandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 28/2/17.
 */

public class ProgramAdapter extends RecyclerView.Adapter<ProgramAdapter.ProgramAdapterViewHolder> {
    private static final String TAG = "ProgramAdapter";
    private List<Performance> mPerformanceData;

    private final ProgramAdapterOnClickHandler mClickHandler;

    ProgramAdapter(ProgramAdapterOnClickHandler clickHandler) { mClickHandler = clickHandler; }

    public void setPerformanceData(List<Performance> performances) {
        Log.d(TAG, "setPerformanceData: " + String.valueOf(performances));
        mPerformanceData = performances;
        notifyDataSetChanged();
    }

    interface ProgramAdapterOnClickHandler {
        void onClick(String artistId);
    }

    public class ProgramAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView mArtistNameTextView;
        TextView mPerformanceTimeTextView;
        TextView mVenueName;
        ImageView mImageView;
        Performance mPerformance;
        Artist mArtist;

        ProgramAdapterViewHolder (View view) {
            super(view);
            mArtistNameTextView = (TextView) view.findViewById(R.id.tv_artist_name);
            mImageView = (ImageView) view.findViewById(R.id.program_image);
            mPerformanceTimeTextView = (TextView) view.findViewById(R.id.tv_performance_time);
            mVenueName = (TextView) view.findViewById(R.id.tv_venue_name);
            Typeface bebasNeue = Typeface.createFromAsset(mArtistNameTextView.getContext().getAssets(), "fonts/BebasNeueRegular.otf");
            mArtistNameTextView.setTypeface(bebasNeue);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mArtist.getId());
        }

        void bind(Performance performance) {
            mPerformance = performance;
            mArtist = ArtistRepo.getArtist(performance.getArtistId());
            mArtistNameTextView.setText(mArtist.getName());
            mImageView.setImageDrawable(mArtist.getArtistDrawable(mImageView.getContext()));
            mPerformanceTimeTextView.setText(mPerformance.formattedTime());
            mVenueName.setText(mPerformance.getVenue());
        }
    }

    @Override
    public ProgramAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.program_list_item;
        boolean shouldAttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachImmediately);
        return new ProgramAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProgramAdapterViewHolder holder, int position) {
        Performance performanceForPosition = mPerformanceData.get(position);
        holder.bind(performanceForPosition);
    }

    @Override
    public int getItemCount() {
        if(null == mPerformanceData) return 0;
        return mPerformanceData.size();
    }
}
