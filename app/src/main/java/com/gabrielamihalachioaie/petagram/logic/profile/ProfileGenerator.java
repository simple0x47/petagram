package com.gabrielamihalachioaie.petagram.logic.profile;

import com.gabrielamihalachioaie.petagram.R;

import java.util.ArrayList;

public class ProfileGenerator {
    private static final int NAME_IDS = 100;
    private static final int MAX_INITIAL_LIKES = 25;
    private static final int MAX_INITIAL_POSTS = 10;
    private static final int MAX_PROFILES = 100000;
    private static final int MAX_POSTS_PER_PROFILE = 1000;

    private static ProfileGenerator instance;

    private ProfileGenerator() {

    }

    public ArrayList<Profile> generatePets(int quantity) {
        ArrayList<Profile> pets = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            Post profilePost = new Post(i, generateProfilePicture(), generateLikes());
            Profile pet = new Profile(i, generateName(),
                    profilePost, generatePosts(i));
            pets.add(pet);
        }

        return pets;
    }

    private int generateProfilePicture() {
        int profilePicture;

        double random = Math.random();

        if (random < 0.33) {
            profilePicture = R.drawable.big_doge;
        } else if (random < 0.66) {
            profilePicture = R.drawable.small_doge;
        } else {
            profilePicture = R.drawable.real_doge;
        }

        return profilePicture;
    }

    private String generateName() {
        return String.format("Doge %d", (int) (Math.random() * NAME_IDS));
    }

    private int generateLikes() {
        return (int) (Math.random() * MAX_INITIAL_LIKES);
    }

    private ArrayList<Post> generatePosts(int profileId) {
        ArrayList<Post> posts = new ArrayList<>();

        int postsCount = (int) (Math.random() * MAX_INITIAL_POSTS) + 1;

        for (int i = 0; i < postsCount; i++) {
            posts.add(new Post(MAX_PROFILES + (profileId * MAX_POSTS_PER_PROFILE) + i,
                    generateProfilePicture(), generateLikes()));
        }

        return posts;
    }

    public static ProfileGenerator getInstance() {
        if (instance == null) {
            instance = new ProfileGenerator();
        }

        return instance;
    }
}