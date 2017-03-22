package com.yackfolkfestival.android.yffandroid;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by chris on 21/3/17.
 */
public class MoreListAdapter extends RecyclerView.Adapter<MoreListAdapter.MoreListViewHolder> {
    private static final String PLACEHOLDER_IMAGE_NAME = "program_icon";
    private final List<TypedArray> mMoreItems;

    private final MoreListOnClickHandler mClickHandler;

    MoreListAdapter(MoreListOnClickHandler clickHandler, List<TypedArray> list) {
        mClickHandler = clickHandler;
        mMoreItems = list;
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
        String title = mMoreItems.get(position).getString(0);
        String imageName = mMoreItems.get(position).getString(1);
        String outURL = mMoreItems.get(position).getString(2);
        holder.bind(title, imageName, outURL);
    }

    @Override
    public int getItemCount() {
        return mMoreItems.size();
    }
    
    public class MoreListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        TextView mTextView;
        ImageView mImageView;
        String outURL;

        MoreListViewHolder (View view) {
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_more);
            mImageView = (ImageView) view.findViewById(R.id.icon_more);
            Typeface bebasNeue = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/BebasNeueRegular.otf");
            mTextView.setTypeface(bebasNeue);
            view.setOnClickListener(this);
        }

        void bind(String moreTitle, String imageName, String aURL) {
            mTextView.setText(moreTitle);
            outURL = aURL;

            setImage(imageName);
        }

        private void setImage(String imageName) {
            Resources res = mImageView.getResources();
            Context context = mImageView.getContext();
            Drawable drawable = null;
            if (imageName != null && !imageName.equals("")) {
                int resID = res.getIdentifier(imageName, "drawable", context.getPackageName());
                if (resID != 0) {
                    drawable = ContextCompat.getDrawable(context, resID);
                } else {
                    resID = res.getIdentifier(PLACEHOLDER_IMAGE_NAME, "drawable", context.getPackageName());
                    drawable = ContextCompat.getDrawable(context, resID);
                }
            }
            if (drawable != null) {
                mImageView.setImageDrawable(drawable);
            }
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(outURL);
        }
    }
}
