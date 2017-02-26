package com.example.android.yffandroid;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActionBarDrawerToggle toggle=null;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getFragmentManager().findFragmentById(R.id.content_fragment) == null) {
            showArtistList();
        }

        ListView drawer = (ListView) findViewById(R.id.drawer);
        drawer.setAdapter(
                new ArrayAdapter<>(
                        this,
                        R.layout.drawer_row,
                        getResources().getStringArray(R.array.drawer_rows)));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

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

    private void showArtistList() {
        android.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        ArtistsListFragment fragment = new ArtistsListFragment();
        transaction.replace(R.id.content_fragment, fragment);
        transaction.commit();
    }
}
