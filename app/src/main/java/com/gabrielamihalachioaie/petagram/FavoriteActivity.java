package com.gabrielamihalachioaie.petagram;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    public static final String FAVORITES_EXTRA = "favorites";

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

        ArrayList<Mascota> favorites = getIntent().getParcelableArrayListExtra(FAVORITES_EXTRA);

        if (favorites == null) {
            throw new IllegalStateException("No favorites data found.");
        }

        RecyclerView favoritesList = findViewById(R.id.favoritesList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        favoritesList.setLayoutManager(linearLayoutManager);

        PetAdapter adapter = new PetAdapter(favorites);
        favoritesList.setAdapter(adapter);
    }
}