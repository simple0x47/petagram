package com.gabrielamihalachioaie.petagram.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.custom.IconWithTextDrawable;
import com.gabrielamihalachioaie.petagram.logic.profile.ProfileGenerator;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;
import com.gabrielamihalachioaie.petagram.storage.ProfileDbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String DATA_SET = "dataset";

    private ArrayList<Profile> profiles;
    private ProfileDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Camera not available.", Snackbar.LENGTH_LONG)
                .setAction("WIP", null).show());


        if (savedInstanceState != null) {
            ArrayList<Profile> uncheckedArrayList = savedInstanceState.getParcelableArrayList(DATA_SET);

            if (uncheckedArrayList != null) {
                profiles = uncheckedArrayList;
            }
        }

        dbHelper = new ProfileDbHelper(this);

        if (profiles == null) {
            ArrayList<Profile> uncheckedArrayList = getIntent().getParcelableArrayListExtra(DATA_SET);

            if (uncheckedArrayList != null) {
                profiles = uncheckedArrayList;
            } else {
                if (dbHelper.hasDatabaseAnyProfiles()) {
                    profiles = dbHelper.getProfilesFromDatabase();
                } else {
                    profiles = ProfileGenerator.getInstance().generatePets(10);

                    dbHelper.insertProfilesToDatabase(profiles);
                }
            }
        }

        initializeUI();
    }

    private void initializeUI() {
        MainFragment fragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mainFragment);

        fragment.updateData(profiles);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem favoritePets = menu.findItem(R.id.favoritePets);
        BitmapDrawable drawable = (BitmapDrawable) getDrawable(R.drawable.star);

        // We draw a 5 because that's the amount of favorites there will be.
        IconWithTextDrawable favorite = new IconWithTextDrawable("5", drawable.getBitmap(), Typeface.DEFAULT_BOLD);
        favoritePets.setIcon(favorite);

        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelableArrayList(DATA_SET, profiles);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        ArrayList<Profile> uncheckedArrayList = savedInstanceState.getParcelableArrayList(DATA_SET);

        if (uncheckedArrayList != null) {
            profiles = uncheckedArrayList;
        }

        initializeUI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.favoritePets:
                showFavoritesActivity();
                break;
            case R.id.contactItem:
                showContactActivity();
                break;
            case R.id.aboutUsItem:
                showAboutUsActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFavoritesActivity() {
        Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
        intent.putParcelableArrayListExtra(FavoriteActivity.DATA_SET, profiles);

        startActivity(intent);
        finish();
    }

    private void showContactActivity() {
        Intent intent = new Intent(getApplicationContext(), ContactActivity.class);

        startActivity(intent);
    }

    private void showAboutUsActivity() {
        Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onStop() {
        dbHelper.saveProfilesToDatabase(profiles);
        dbHelper.close();
        super.onStop();
    }
}