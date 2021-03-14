package com.gabrielamihalachioaie.petagram.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.gabrielamihalachioaie.petagram.logic.profile.Post;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public class ProfileDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Petagram.db";

    public ProfileDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PostContract.PostEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ProfileContract.ProfileEntry.SQL_CREATE_ENTRIES);
        db.execSQL(ProfileOwnsContract.ProfileOwnsEntry.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ProfileOwnsContract.ProfileOwnsEntry.SQL_DELETE_ENTRIES);
        db.execSQL(ProfileContract.ProfileEntry.SQL_DELETE_ENTRIES);
        db.execSQL(PostContract.PostEntry.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean hasDatabaseAnyProfiles() {
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + ProfileContract.ProfileEntry.TABLE_NAME;

        Cursor cursor = db.rawQuery(query, null);

        int profilesCount = -1;

        if (cursor.moveToNext()) {
            profilesCount = cursor.getInt(0);
        }


        cursor.close();
        return profilesCount > 0;
    }

    public ArrayList<Profile> getProfilesFromDatabase() {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                ProfileContract.ProfileEntry.COLUMN_NAME_NAME,
                ProfileContract.ProfileEntry.COLUMN_NAME_PROFILE_POST
        };

        Cursor profileCursor = db.query(
                ProfileContract.ProfileEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        ArrayList<Profile> profiles = new ArrayList<>();

        while (profileCursor.moveToNext()) {
            int profileId = profileCursor.getInt(profileCursor.getColumnIndex(BaseColumns._ID));
            String profileName = profileCursor
                    .getString(profileCursor.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_NAME_NAME));
            int profilePostId = profileCursor
                    .getInt(profileCursor.getColumnIndex(ProfileContract.ProfileEntry.COLUMN_NAME_PROFILE_POST));

            Post profilePost = getPostFromId(profilePostId);
            ArrayList<Post> posts = getPostsFromProfile(profileId, profilePostId);

            Profile profile = new Profile(profileId, profileName, profilePost, posts);
            profiles.add(profile);
        }

        profileCursor.close();
        return profiles;
    }

    private ArrayList<Post> getPostsFromProfile(int profileId, int profilePostId) {
        String[] projection = {
                BaseColumns._ID,
                ProfileOwnsContract.ProfileOwnsEntry.COLUMN_NAME_PROFILE,
                ProfileOwnsContract.ProfileOwnsEntry.COLUMN_NAME_POST
        };

        String selection = ProfileOwnsContract.ProfileOwnsEntry.COLUMN_NAME_PROFILE + " = ? AND " +
                ProfileOwnsContract.ProfileOwnsEntry.COLUMN_NAME_POST + " != ?";

        String[] selectionArgs = {
                String.format("%d", profileId),
                String.format("%d", profilePostId)
        };

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ProfileOwnsContract.ProfileOwnsEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        ArrayList<Post> posts = new ArrayList<>();

        while (cursor.moveToNext()) {
            int postId = cursor
                    .getInt(cursor.getColumnIndex(ProfileOwnsContract.ProfileOwnsEntry.COLUMN_NAME_POST));

            Post post = getPostFromId(postId);

            posts.add(post);
        }

        cursor.close();

        return posts;
    }

    private Post getPostFromId(int postId) {
        String[] projection = {
                BaseColumns._ID,
                PostContract.PostEntry.COLUMN_NAME_LIKES,
                PostContract.PostEntry.COLUMN_NAME_PICTURE
        };

        String selection = PostContract.PostEntry._ID + " = ?";
        String[] selectionArgs = { String.format("%d", postId) };

        SQLiteDatabase db = getReadableDatabase();
        Cursor postCursor = db.query(PostContract.PostEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null);

        postCursor.moveToNext();

        int likes = postCursor.getInt(postCursor.getColumnIndex(PostContract.PostEntry.COLUMN_NAME_LIKES));
        int pictureId = postCursor.getInt(postCursor.getColumnIndex(PostContract.PostEntry.COLUMN_NAME_PICTURE));

        Post post = new Post(postId, pictureId, likes);

        postCursor.close();

        return post;
    }

    public void saveProfilesToDatabase(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> postsValues = PostContract.getContentValuesFromProfiles(profiles);
        ArrayList<ContentValues> profilesValues = ProfileContract.getContentValuesFromProfiles(profiles);
        ArrayList<ContentValues> profilesOwnsValues = ProfileOwnsContract.getContentValuesFromProfiles(profiles);

        updateValues(PostContract.PostEntry.TABLE_NAME, postsValues);
        updateValues(ProfileContract.ProfileEntry.TABLE_NAME, profilesValues);
        updateValues(ProfileOwnsContract.ProfileOwnsEntry.TABLE_NAME, profilesOwnsValues);
    }

    private void updateValues(String table, ArrayList<ContentValues> values) {
        SQLiteDatabase db = getWritableDatabase();

        for (ContentValues value : values) {
            String whereClause = BaseColumns._ID + " = ?";
            String[] whereArgs = new String[] {
                    value.getAsString(BaseColumns._ID)
            };

            db.update(table, value, whereClause, whereArgs);
        }
    }

    public void insertProfilesToDatabase(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> postsValues = PostContract.getContentValuesFromProfiles(profiles);
        ArrayList<ContentValues> profilesValues = ProfileContract.getContentValuesFromProfiles(profiles);
        ArrayList<ContentValues> profilesOwnsValues = ProfileOwnsContract.getContentValuesFromProfiles(profiles);

        insertValuesIntoTable(PostContract.PostEntry.TABLE_NAME, postsValues);
        insertValuesIntoTable(ProfileContract.ProfileEntry.TABLE_NAME, profilesValues);
        insertValuesIntoTable(ProfileOwnsContract.ProfileOwnsEntry.TABLE_NAME, profilesOwnsValues);
    }

    private void insertValuesIntoTable(String table, ArrayList<ContentValues> values) {
        SQLiteDatabase db = getWritableDatabase();

        for (ContentValues value : values) {
            db.insert(table, null, value);
        }
    }
}