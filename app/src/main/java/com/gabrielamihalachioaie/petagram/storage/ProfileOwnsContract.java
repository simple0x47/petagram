package com.gabrielamihalachioaie.petagram.storage;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.gabrielamihalachioaie.petagram.logic.profile.Post;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public final class ProfileOwnsContract {
    private ProfileOwnsContract() {}

    public static class ProfileOwnsEntry implements BaseColumns {
        public static final String TABLE_NAME = "profile_owns";
        public static final String COLUMN_NAME_PROFILE = "profile";
        public static final String COLUMN_NAME_POST = "post";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_PROFILE + " INTEGER," +
                        COLUMN_NAME_POST + " INTEGER," +
                        "FOREIGN KEY (" + COLUMN_NAME_PROFILE + ") REFERENCES " +
                        ProfileContract.ProfileEntry.TABLE_NAME + "(" + ProfileContract.ProfileEntry._ID + ")," +
                        "FOREIGN KEY (" + COLUMN_NAME_POST + ") REFERENCES " +
                        PostContract.PostEntry.TABLE_NAME + "(" + PostContract.PostEntry._ID + ")" +
                        ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static ArrayList<ContentValues> getContentValuesFromProfiles(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for (Profile profile : profiles) {
            Post profilePost = profile.getProfilePost();

            ContentValues profileValues = getContentValuesFromProfileAndPost(profile, profilePost);
            values.add(profileValues);

            for (Post post : profile.getPosts()) {
                ContentValues postValues = getContentValuesFromProfileAndPost(profile, post);
                values.add(postValues);
            }
        }

        return values;
    }

    private static ContentValues getContentValuesFromProfileAndPost(Profile profile, Post post) {
        ContentValues values = new ContentValues();

        values.put(ProfileOwnsEntry.COLUMN_NAME_PROFILE, profile.getId());
        values.put(ProfileOwnsEntry.COLUMN_NAME_POST, post.getId());

        return values;
    }
}