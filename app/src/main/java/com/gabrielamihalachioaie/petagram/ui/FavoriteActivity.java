package com.gabrielamihalachioaie.petagram.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;
import com.gabrielamihalachioaie.petagram.logic.profile.ProfileComparator;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    public static final String DATA_SET = "profiles";

    private ArrayList<Profile> profiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        Toolbar toolbar = findViewById(R.id.favoriteToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        profiles = getIntent().getParcelableArrayListExtra(DATA_SET);

        if (profiles == null) {
            throw new IllegalStateException("No favorites data found.");
        }

        ArrayList<Profile> favorites = ProfileComparator.getProfilesWithMostLikes(profiles, 5);

        RecyclerView favoritesList = findViewById(R.id.favoritesList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesList.setLayoutManager(linearLayoutManager);

        ProfileAdapter adapter = new ProfileAdapter(favorites);
        favoritesList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            showMainActivity();
            // Allow to restore the state of the main activity.
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        showMainActivity();
        finish();
    }

    private void showMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putParcelableArrayListExtra(MainActivity.DATA_SET, profiles);

        startActivity(intent);
    }
}