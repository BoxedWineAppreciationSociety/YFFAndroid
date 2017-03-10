package com.example.android.yffandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by chris on 11/3/17.
 */

public class DrawerAdapter extends RecyclerView.Adapter<DrawerAdapter.DrawerAdapterViewHolder> {
    private final DrawerAdapterOnClickHandler mClickHandler;
    private final String[] mDrawerRows;

    DrawerAdapter(DrawerAdapterOnClickHandler clickHandler, String[] drawerRows) {
        mClickHandler = clickHandler;
        mDrawerRows = drawerRows;
    }

    interface DrawerAdapterOnClickHandler {
        void onClick(int position);
    }

    public class DrawerAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener  {
        TextView mTextView;

        DrawerAdapterViewHolder(View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_drawer_item);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(adapterPosition);
        }
    }

    @Override
    public DrawerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.drawer_list_item;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new DrawerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrawerAdapterViewHolder holder, int position) {
        holder.mTextView.setText(textForPosition(position));
    }

    private String textForPosition(int position) {
        return mDrawerRows[position];
    }


    @Override
    public int getItemCount() {
        return 4;
    }
}
