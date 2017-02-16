package com.example.android.yffandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 1/2/17.
 */

public class ArtistListAdapter extends RecyclerView.Adapter<ArtistListAdapter.ArtistListAdapterViewHolder> {
    private List<Artist> mArtistData;

    ArtistListAdapter() {}

    public class ArtistListAdapterViewHolder extends RecyclerView.ViewHolder {
        public final TextView mArtistNameTextView;

        ArtistListAdapterViewHolder (View view) {
            super(view);
            mArtistNameTextView = (TextView) view.findViewById(R.id.tv_artist_name);
        }
    }

    @Override
    public ArtistListAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.artist_list_item;
        boolean shouldAttachImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachImmediately);
        return new ArtistListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ArtistListAdapterViewHolder holder, int position) {
        Artist artistForPosition = mArtistData.get(position);
        String artistName = artistForPosition.getName();
        holder.mArtistNameTextView.setText(artistName);
    }

    @Override
    public int getItemCount() {
        if(null == mArtistData) return 0;
        return mArtistData.size();
    }

    public void setArtistData(List<Artist> artists) {
        mArtistData = artists;
        notifyDataSetChanged();
    }
}
