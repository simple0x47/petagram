package com.gabrielamihalachioaie.petagram;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.gabrielamihalachioaie.petagram.custom.IconWithTextDrawable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String DATA_SET = "dataset";

    private ArrayList<Mascota> dataSet;

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
            ArrayList<Mascota> uncheckedArrayList = savedInstanceState.getParcelableArrayList(DATA_SET);

            if (uncheckedArrayList != null) {
                dataSet = uncheckedArrayList;
            }
        }

        if (dataSet == null) {
            dataSet = PetGenerator.getInstance().generatePets(10);
        }

        RecyclerView petsList = findViewById(R.id.petsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        petsList.setLayoutManager(linearLayoutManager);

        PetAdapter adapter = new PetAdapter(dataSet);
        petsList.setAdapter(adapter);
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
        outState.putParcelableArrayList(DATA_SET, dataSet);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.favoritePets:
                showFavoritesActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFavoritesActivity() {
        ArrayList<Mascota> favorites = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            favorites.add(dataSet.get(i));
        }

        Intent intent = new Intent(getApplicationContext(), FavoriteActivity.class);
        intent.putParcelableArrayListExtra(FavoriteActivity.FAVORITES_EXTRA, favorites);

        startActivity(intent);
    }
}