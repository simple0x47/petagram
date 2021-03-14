package com.gabrielamihalachioaie.petagram.storage;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public final class ProfileContract {
    private ProfileContract() {}

    public static class ProfileEntry implements BaseColumns {
        public static final String TABLE_NAME = "profile";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PROFILE_POST = "profilePost";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_NAME + " TEXT," +
                        COLUMN_NAME_PROFILE_POST + " INTEGER," +
                        "FOREIGN KEY (" + COLUMN_NAME_PROFILE_POST + ") REFERENCES " +
                        PostContract.PostEntry.TABLE_NAME + "(" + PostContract.PostEntry._ID + ")" +
                        ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static ArrayList<ContentValues> getContentValuesFromProfiles(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for (Profile profile : profiles) {
            ContentValues profileValues = getContentValuesFromProfile(profile);

            values.add(profileValues);
        }

        return values;
    }

    private static ContentValues getContentValuesFromProfile(Profile profile) {
        ContentValues profileValues = new ContentValues();
        profileValues.put(ProfileEntry._ID, profile.getId());
        profileValues.put(ProfileEntry.COLUMN_NAME_NAME, profile.getName());
        profileValues.put(ProfileEntry.COLUMN_NAME_PROFILE_POST, profile.getProfilePost().getId());

        return profileValues;
    }
}