package com.example.android.yffandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by chris on 28/2/17.
 */

public class ProgramAdapter extends RecyclerView.Adapter {
    private List<Artist> mArtistData;

    private final ProgramAdapterOnClickHandler mClickHandler;

    ProgramAdapter(ProgramAdapterOnClickHandler clickHandler) { mClickHandler = clickHandler; }

    public void setArtistData(List<Artist> artists) {
        mArtistData = artists;
        notifyDataSetChanged();
    }

    interface ProgramAdapterOnClickHandler {
        void onClick(String artistId);
    }

    public class ProgramAdapterViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView mArtistNameTextView;

        ProgramAdapterViewHolder (View view) {
            super(view);
            mArtistNameTextView = (TextView) view.findViewById(R.id.tv_artist_name);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Artist artist = mArtistData.get(adapterPosition);
            mClickHandler.onClick(artist.getId());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.artist_list_item;
        boolean shouldAttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachImmediately);
        return new ProgramAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Artist artistForPosition = mArtistData.get(position);
        String artistName = artistForPosition.getName();
    }

    @Override
    public int getItemCount() {
        if(null == mArtistData) return 0;
        return mArtistData.size();
    }
}
