package com.example.android.yffandroid;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements DrawerAdapter.DrawerAdapterOnClickHandler {
    private ActionBarDrawerToggle toggle=null;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getFragmentManager().findFragmentById(R.id.content_fragment) == null) {
            showArtistList();
        }

        RecyclerView drawerRV = (RecyclerView) findViewById(R.id.drawer);
        drawerRV.setLayoutManager(new LinearLayoutManager(this));
        drawerRV.setHasFixedSize(true);

        String[] drawerRows = getResources().getStringArray(R.array.drawer_rows);
        DrawerAdapter drawerAdapter = new DrawerAdapter(this, drawerRows);
        drawerRV.setAdapter(drawerAdapter);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.setDrawerListener(toggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return(super.onCreateOptionsMenu(menu));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(int position) {
        switch (position) {
            case 0:
                showProgram();
                break;
            case 1:
                showEventMap();
                break;
            case 2:
                showArtistList();
                break;
            default:
                showViewPager();
                break;
        }
        drawerLayout.closeDrawer(Gravity.LEFT);
    }

    private void showProgram() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ProgramFragment fragment = new ProgramFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }

    private void showArtistList() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ArtistsListFragment fragment = new ArtistsListFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }

    private void showEventMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://yackfolkfestival.com/festival-info/map/"));
        startActivity(intent);
    }

    private void showViewPager() {
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PagerFragment fragment = new PagerFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }
}
