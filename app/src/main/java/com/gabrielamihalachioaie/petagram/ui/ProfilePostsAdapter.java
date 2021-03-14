package com.gabrielamihalachioaie.petagram.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gabrielamihalachioaie.petagram.R;
import com.gabrielamihalachioaie.petagram.logic.profile.Post;

import java.util.ArrayList;

public class ProfilePostsAdapter
        extends RecyclerView.Adapter<ProfilePostsAdapter.ProfilePostsViewHolder> {
    public static class ProfilePostsViewHolder extends RecyclerView.ViewHolder {
        private final ImageView profilePostPicture;
        private final TextView profilePostLikes;
        private final ImageView profilePostFilledBone;

        public ProfilePostsViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePostPicture = itemView.findViewById(R.id.profilePostImage);
            profilePostLikes = itemView.findViewById(R.id.profilePostLikes);
            profilePostFilledBone = itemView.findViewById(R.id.profilePostFilledBone);
        }
    }

    private ArrayList<Post> profilePosts;

    public ProfilePostsAdapter(ArrayList<Post> profilePosts) {
        this.profilePosts = profilePosts;
    }

    @NonNull
    @Override
    public ProfilePostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.profile_post_item, parent, false);

        return new ProfilePostsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfilePostsViewHolder holder, int position) {
        Post post = profilePosts.get(position);

        holder.profilePostPicture.setImageResource(post.getPictureResourceId());
        holder.profilePostLikes.setText(String.format("%d", post.getLikes()));
    }

    @Override
    public int getItemCount() {
        return profilePosts.size();
    }
}
