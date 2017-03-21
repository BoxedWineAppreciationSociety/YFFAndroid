package com.example.android.yffandroid;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by chris on 21/3/17.
 */
public class MoreFragment extends Fragment
    implements MoreListAdapter.MoreListOnClickHandler {
    private RecyclerView mRecyclerView;
    private MoreListAdapter mMoreListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_more_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rv_more);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        List<TypedArray> moreRows = ResourceHelper.getMultiTypedArray(rootView.getContext(), "more_items_multi");
        mMoreListAdapter = new MoreListAdapter(this, moreRows);
        mRecyclerView.setAdapter(mMoreListAdapter);

        setupActionBar();

        return rootView;
    }

    @Override
    public void onClick(String outURL) {
        if(outURL == null || outURL.equals("")) { return; }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(outURL));
        startActivity(intent);
    }

    private void setupActionBar() {
        ActionBar programActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        programActionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorYFFGreen)));

        programActionBar.setTitle(R.string.more_action_title);
    }
}
