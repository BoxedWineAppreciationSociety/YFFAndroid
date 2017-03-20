package com.example.android.yffandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 12/3/17.
 */
public class ArtistPerformancesAdapter extends RecyclerView.Adapter<ArtistPerformancesAdapter.ArtistPerformancesAdapterViewHolder> {
    List<Performance> mPerformances;

    ArtistPerformancesAdapter(List<Performance> performances) {
        mPerformances = performances;
    }

    @Override
    public ArtistPerformancesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.performance_list_item, parent, false);
        return new ArtistPerformancesAdapter.ArtistPerformancesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistPerformancesAdapterViewHolder holder, int position) {
        Performance performance = mPerformances.get(position);
        holder.bind(performance);
    }

    @Override
    public int getItemCount() {
        return mPerformances.size();
    }

    public void setPerformanceData(List<Performance> performanceData) {
        this.mPerformances = performanceData;
        notifyDataSetChanged();
    }

    public class ArtistPerformancesAdapterViewHolder extends RecyclerView.ViewHolder {
        Performance mPerformance;
        TextView mPerformanceTime;
        TextView mPerformanceDetails;

        public ArtistPerformancesAdapterViewHolder(View itemView) {
            super(itemView);
            mPerformanceTime = (TextView) itemView.findViewById(R.id.tv_performance_time);
            mPerformanceDetails = (TextView) itemView.findViewById(R.id.tv_date_and_venue);
            Typeface bebasNeue = Typeface.createFromAsset(mPerformanceTime.getContext().getAssets(), "fonts/BebasNeueRegular.otf");
            mPerformanceTime.setTypeface(bebasNeue);
            mPerformanceDetails.setTypeface(bebasNeue);
        }

        void bind(Performance performance) {
            mPerformance = performance;
            mPerformanceTime.setText(mPerformance.formattedTime());
            mPerformanceDetails.setText(mPerformance.dateAndVenue());
        }
    }
}
