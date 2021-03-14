package com.gabrielamihalachioaie.petagram.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;
import com.gabrielamihalachioaie.petagram.logic.profile.ProfileComparator;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private MainTabsAdapter adapter;
    private ViewPager2 viewPager;

    private ArrayList<Profile> profiles;
    private ProfilesListFragment profilesListFragment;

    private ProfileHolder favoriteProfile;
    private ProfileFragment profileFragment;

    public MainFragment() {
        favoriteProfile = new ProfileHolder();
        profileFragment = new ProfileFragment(favoriteProfile);

        ProfileLikeListener likeListener = this::checkPossibleUpdateForProfile;

        profiles = new ArrayList<>();
        profilesListFragment = new ProfilesListFragment(profiles, likeListener);
    }

    public void updateData(ArrayList<Profile> profiles) {
        this.profiles.addAll(profiles);

        updateFavoriteProfile();
        profilesListFragment.setProfiles(profiles);
        profilesListFragment.setListener(this::checkPossibleUpdateForProfile);
    }

    private void updateFavoriteProfile() {
        Profile profileWithMostLikes = ProfileComparator.getWithMostLikes(profiles);

        if (favoriteProfile.getProfile() != profileWithMostLikes) {
            favoriteProfile.setProfile(profileWithMostLikes);
            profileFragment.updateViewToProfile();
        }
    }

    public void checkPossibleUpdateForProfile(Profile likedProfile) {
        if (likedProfile.compareTo(favoriteProfile.getProfile()) > 0) {
            favoriteProfile.setProfile(likedProfile);
            profileFragment.updateViewToProfile();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        adapter = new MainTabsAdapter(this, profilesListFragment, profileFragment);
        viewPager = view.findViewById(R.id.contentViewPager);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.mainTabLayout);

        new TabLayoutMediator(tabLayout, viewPager, ((tab, position) -> {
            if (position == 0) {
                tab.setIcon(R.drawable.home);
            } else if (position == 1) {
                tab.setIcon(R.drawable.dog);
            }
        })).attach();
    }
}