package com.example.android.yffandroid;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by chris on 21/3/17.
 */
public class MoreListAdapter extends RecyclerView.Adapter<MoreListAdapter.MoreListViewHolder> {
    private String[] mMoreItems;

    private final MoreListOnClickHandler mClickHandler;

    MoreListAdapter(MoreListOnClickHandler clickHandler, String[] titles) {
        mClickHandler = clickHandler;
        mMoreItems = titles;
    }

    interface MoreListOnClickHandler {
        void onClick(String moreId);
    }

    @Override
    public MoreListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutId = R.layout.more_list_item;
        View view = inflater.inflate(layoutId, parent, false);
        return new MoreListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoreListViewHolder holder, int position) {
        String moreTitle = mMoreItems[position];
        holder.bind(moreTitle);
    }

    @Override
    public int getItemCount() {
        return mMoreItems.length;
    }

    public class MoreListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        TextView mTextView;

        MoreListViewHolder (View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_more);
            Typeface bebasNeue = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/BebasNeueRegular.otf");
            mTextView.setTypeface(bebasNeue);
            view.setOnClickListener(this);
        }

        void bind(String moreTitle) {
            mTextView.setText(moreTitle);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(Integer.toString(adapterPosition));
        }
    }
}
