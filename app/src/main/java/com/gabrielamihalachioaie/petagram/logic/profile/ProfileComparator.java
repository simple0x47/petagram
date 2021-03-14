package com.gabrielamihalachioaie.petagram.logic.profile;

import java.util.ArrayList;

public class ProfileComparator {
    public static Profile getWithMostLikes(ArrayList<Profile> profiles) {
        Profile highestLikedProfile = profiles.get(0);

        for (Profile profile : profiles) {
            if (profile.compareTo(highestLikedProfile) > 0) {
                highestLikedProfile = profile;
            }
        }

        return highestLikedProfile;
    }

    public static ArrayList<Profile> getProfilesWithMostLikes(ArrayList<Profile> profiles, int amount) {
        ArrayList<Profile> profilesWithMostLikes = new ArrayList<>(amount);

        for (Profile profile : profiles) {
            for (int i = 0; i < profiles.size(); i++) {
                try {
                    Profile p = profilesWithMostLikes.get(i);

                    if (profile.compareTo(p) > 0) {
                        profilesWithMostLikes.add(i, profile);
                        break;
                    }
                } catch (IndexOutOfBoundsException ignored) {
                    profilesWithMostLikes.add(profile);
                    break;
                }
            }
        }

        // Remove the excess from the ArrayList.
        if (profilesWithMostLikes.size() > amount) {
            profilesWithMostLikes.subList(amount, profilesWithMostLikes.size()).clear();
        }

        return profilesWithMostLikes;
    }
}