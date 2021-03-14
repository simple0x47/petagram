package com.gabrielamihalachioaie.petagram.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Post;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder> {
    public static class ProfileViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilePicture;
        private final ImageButton giveBoneButton;
        private final TextView profileName;
        private final TextView givenBones;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.profilePicture);
            giveBoneButton = itemView.findViewById(R.id.giveBoneButton);
            profileName = itemView.findViewById(R.id.profileName);
            givenBones = itemView.findViewById(R.id.givenBones);
        }
    }

    private final ArrayList<Profile> dataSet;
    private ProfileLikeListener listener;

    public ProfileAdapter(ArrayList<Profile> profiles)
    {
        this.dataSet = profiles;
    }

    public ProfileAdapter(ArrayList<Profile> profiles, ProfileLikeListener likeListener)
    {
        this.dataSet = profiles;
        this.listener = likeListener;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.profile_item, viewGroup, false);

        return new ProfileViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder profileViewHolder, int i) {
        Profile pet = dataSet.get(i);

        Post profilePost = pet.getProfilePost();
        profileViewHolder.profilePicture.setImageResource(profilePost.getPictureResourceId());
        profileViewHolder.profileName.setText(pet.getName());
        profileViewHolder.givenBones.setText(String.format("%d", profilePost.getLikes()));

        profileViewHolder.giveBoneButton.setOnClickListener((v) -> {
            profilePost.addLike();
            profileViewHolder.givenBones.setText(String.format("%d", profilePost.getLikes()));

            if (listener != null) {
                listener.onLike(pet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}