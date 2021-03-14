package com.gabrielamihalachioaie.petagram.ui;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MainTabsAdapter extends FragmentStateAdapter {
    private Fragment profilesListFragment;
    private Fragment profileFragment;

    public MainTabsAdapter(@NonNull Fragment fragment,
                           Fragment profilesListFragment, Fragment profileFragment) {
        super(fragment);

        this.profilesListFragment = profilesListFragment;
        this.profileFragment = profileFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return profilesListFragment;
        } else if (position == 1) {
            return profileFragment;
        } else {
            throw new IllegalArgumentException("Unexpected 'position' value: " + position);
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}