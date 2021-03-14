package com.gabrielamihalachioaie.petagram.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Post;

import java.util.ArrayList;

public class ProfileFragment extends Fragment {

    private RecyclerView profilePostsGrid;
    private ImageView profileFragmentPicture;
    private TextView profileFragmentName;

    private final ProfileHolder favoriteProfile;

    public ProfileFragment(ProfileHolder favoriteProfile) {
        this.favoriteProfile = favoriteProfile;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        profileFragmentName = view.findViewById(R.id.profileFragmentName);
        profileFragmentPicture = view.findViewById(R.id.profileCirclePicture);

        profilePostsGrid = view.findViewById(R.id.profilePostsGrid);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);

        profilePostsGrid.setLayoutManager(gridLayoutManager);

        updateViewToProfile();

        return view;
    }

    public void updateViewToProfile() {
        if (profileFragmentName != null && profileFragmentPicture != null && profilePostsGrid != null) {
            profileFragmentName.setText(favoriteProfile.getProfile().getName());
            profileFragmentPicture.setImageResource(favoriteProfile.getProfile()
                    .getProfilePost().getPictureResourceId());

            ArrayList<Post> profilePosts = favoriteProfile.getProfile().getPosts();
            ProfilePostsAdapter adapter = new ProfilePostsAdapter(profilePosts);
            profilePostsGrid.setAdapter(adapter);
        }
    }
}