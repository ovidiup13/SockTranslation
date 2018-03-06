package com.glasgow.mhci.socktranslation;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements TranslateAudio.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        // set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener((menuItem) -> {
            // set item as selected to persist highlight
            menuItem.setChecked(true);
            // close drawer when item is tapped
            mDrawerLayout.closeDrawers();

            Log.v(TAG, "Menu item selected " + R.string.menu_audio);

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            switchFragments(menuItem);

            return true;
        }
    );
}

    private void switchFragments(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_audio: {
                TranslateAudio fragment = new TranslateAudio();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
                Log.v(TAG, "Replace fragment with Translate Audio");
                break;
            }
            case R.id.nav_camera: {
                Log.v(TAG, "Replace fragment with Translate Camera");
                break;
            }
            case R.id.nav_history: {
                Log.v(TAG, "Replace fragment with History");
                break;
            }
            case R.id.nav_settings: {
                Log.v(TAG, "Replace fragment with Settings");
                break;
            }
            case R.id.nav_contact: {
                Log.v(TAG, "Replace fragment with Contact");
                break;
            }
            case R.id.nav_about: {
                Log.v(TAG, "Replace fragment with About");
                break;
            }
            default: {
                TranslateAudio fragment = new TranslateAudio();
                getSupportFragmentManager().beginTransaction().add(R.id.content_frame, fragment).commit();
                Log.v(TAG, "Default switch, use fragment Translate Audio");
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
