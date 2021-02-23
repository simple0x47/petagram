package com.gabrielamihalachioaie.petagram;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    public static class PetViewHolder extends RecyclerView.ViewHolder {


        private final ImageView profilePicture;
        private final ImageButton giveBoneButton;
        private final TextView profileName;
        private final TextView givenBones;
        private final ImageView filledBone;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);

            profilePicture = itemView.findViewById(R.id.profilePicture);
            giveBoneButton = itemView.findViewById(R.id.giveBoneButton);
            profileName = itemView.findViewById(R.id.profileName);
            givenBones = itemView.findViewById(R.id.givenBones);
            filledBone = itemView.findViewById(R.id.filledBones);
        }

        public ImageView getProfilePicture() {
            return profilePicture;
        }

        public TextView getProfileName() {
            return profileName;
        }

        public ImageButton getGiveBoneButton() {
            return giveBoneButton;
        }

        public TextView getGivenBones() {
            return givenBones;
        }

        public ImageView getFilledBone() {
            return filledBone;
        }
    }

    private ArrayList<Mascota> dataSet;

    public PetAdapter(ArrayList<Mascota> dataSet) {
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.profile_item, viewGroup, false);

        return new PetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder petViewHolder, int i) {
        Mascota pet = dataSet.get(i);

        petViewHolder.getProfilePicture().setImageResource(pet.getPictureResourceId());
        petViewHolder.getProfileName().setText(pet.getName());
        petViewHolder.getGivenBones().setText(String.format("%d", pet.getBones()));

        petViewHolder.getGiveBoneButton().setOnClickListener((v) -> {
            pet.addBone();

            petViewHolder.getGivenBones().setText(String.format("%d", pet.getBones()));
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}