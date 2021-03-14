package com.gabrielamihalachioaie.petagram.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public class ProfilesListFragment extends Fragment {
    private ArrayList<Profile> profiles;
    private ProfileLikeListener listener;

    private View parentView;
    private RecyclerView petsList;

    public ProfilesListFragment() {
        profiles = new ArrayList<>();
    }

    public ProfilesListFragment(ArrayList<Profile> profiles, ProfileLikeListener listener) {
        this.profiles = profiles;
        this.listener = listener;
    }

    public void setProfiles(ArrayList<Profile> profiles) {
        this.profiles.clear();

        this.profiles.addAll(profiles);
    }

    public void setListener(ProfileLikeListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profiles_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;
        initializeUI();
    }

    private void initializeUI() {
        petsList = parentView.findViewById(R.id.petsList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        petsList.setLayoutManager(linearLayoutManager);

        createAdapter();
    }

    private void createAdapter() {
        ProfileAdapter adapter = new ProfileAdapter(profiles, listener);
        petsList.setAdapter(adapter);
    }
}