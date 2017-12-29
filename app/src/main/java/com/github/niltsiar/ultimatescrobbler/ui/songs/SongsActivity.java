package com.github.niltsiar.ultimatescrobbler.ui.songs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.niltsiar.ultimatescrobbler.R;
import com.github.niltsiar.ultimatescrobbler.ui.configuration.ConfigurationActivity;
import com.github.niltsiar.ultimatescrobbler.ui.songs.playedsongs.PlayedSongsFragment;
import com.github.niltsiar.ultimatescrobbler.ui.songs.scrobbledsongs.ScrobbledSongsFragment;

public class SongsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction()
                                   .add(R.id.fragment, new ScrobbledSongsFragment())
                                   .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.isChecked()) {
            return true;
        }
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (R.id.nav_scrobbled == id) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, new ScrobbledSongsFragment());
            transaction.commit();
        } else if (R.id.nav_queue == id) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment, new PlayedSongsFragment());
            transaction.commit();
        } else if (R.id.nav_settings == id) {
            Intent configurationIntent = ConfigurationActivity.createCallingIntent(getApplicationContext());
            startActivity(configurationIntent);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
