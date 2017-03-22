package com.yackfolkfestival.android.yffandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

/**
 * Created by chris on 11/3/17.
 */

public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.LinksAdapterViewHolder> {
    private final LinksAdapterOnClickHandler mClickHandler;
    private final SocialLinkCollection mLinks;

    LinksAdapter(LinksAdapterOnClickHandler clickHandler, Artist artist) {
        mClickHandler = clickHandler;
        mLinks = new SocialLinkCollection(artist);
    }

    interface LinksAdapterOnClickHandler {
        void onClick(String url);
    }

    public class LinksAdapterViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
        SocialButton mButton;

        LinksAdapterViewHolder(View view) {
            super(view);
            mButton = (SocialButton) view.findViewById(R.id.social_button);
            mButton.setOnClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            SocialLink link = mLinks.getSocialLinks().get(getAdapterPosition());
            mClickHandler.onClick(link.getUrl());
        }

        public void bind(int position) {
            List<SocialLink> links = mLinks.getSocialLinks();
            Collections.sort(links);
            SocialLink link = links.get(position);

            int iconId = link.getIcon();
            if (iconId != 0) {
                Drawable socialIcon = mButton.getResources().getDrawable(iconId);
                if (socialIcon != null) {
                    mButton.setImageDrawable(socialIcon);
                }
            }

            mButton.setUrl(link.getUrl());

            int colorId = link.getColor();
            if (colorId != 0) {
                int color = mButton.getResources().getColor(colorId);
                if (color != 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        StateListDrawable drawable = (StateListDrawable) mButton.getBackground();
                        drawable.setTint(color);
                    } else {
                        mButton.setBackgroundColor(color);
                    }
                }
            }
        }
    }

    @Override
    public LinksAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.links_list_item, viewGroup, false);
        return new LinksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LinksAdapterViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mLinks.getSocialLinks().size();
    }
}
