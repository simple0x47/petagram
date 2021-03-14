package com.gabrielamihalachioaie.petagram.storage;

import android.content.ContentValues;
import android.provider.BaseColumns;

import com.gabrielamihalachioaie.petagram.logic.profile.Post;
import com.gabrielamihalachioaie.petagram.logic.profile.Profile;

import java.util.ArrayList;

public final class PostContract {
    private PostContract() {}

    public static class PostEntry implements BaseColumns {
        public static final String TABLE_NAME = "post";
        public static final String COLUMN_NAME_LIKES = "likes";
        public static final String COLUMN_NAME_PICTURE = "picture";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_LIKES + " INTEGER," +
                        COLUMN_NAME_PICTURE + " INTEGER" +
                        ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

    public static ArrayList<ContentValues> getContentValuesFromProfiles(ArrayList<Profile> profiles) {
        ArrayList<ContentValues> values = new ArrayList<>();

        for (Profile profile : profiles) {
            Post profilePost = profile.getProfilePost();

            ContentValues profileValues = getContentValuesFromPost(profilePost);
            values.add(profileValues);

            for (Post post : profile.getPosts()) {
                ContentValues postValues = getContentValuesFromPost(post);
                values.add(postValues);
            }
        }

        return values;
    }

    private static ContentValues getContentValuesFromPost(Post post) {
        ContentValues postValues = new ContentValues();

        postValues.put(PostEntry._ID, post.getId());
        postValues.put(PostEntry.COLUMN_NAME_LIKES, post.getLikes());
        postValues.put(PostEntry.COLUMN_NAME_PICTURE, post.getPictureResourceId());

        return postValues;
    }
}