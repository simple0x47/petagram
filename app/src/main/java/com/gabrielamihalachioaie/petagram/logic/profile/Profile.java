package com.gabrielamihalachioaie.petagram.logic.profile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Profile implements Parcelable, Comparable<Profile> {
    private final int id;
    private final String name;
    private Post profilePost;
    private ArrayList<Post> posts;

    public Profile(int id, String name, Post profilePost, ArrayList<Post> posts) {
        this.id = id;
        this.name = name;
        this.profilePost = profilePost;
        this.posts = posts;
    }

    protected Profile(Parcel in) {
        id = in.readInt();
        name = in.readString();
        profilePost = in.readParcelable(Post.class.getClassLoader());
        posts = in.createTypedArrayList(Post.CREATOR);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Post getProfilePost() {
        return profilePost;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel in) {
            return new Profile(in);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeParcelable(profilePost, flags);
        dest.writeTypedList(posts);
    }

    @Override
    public int compareTo(Profile o) {
        return Integer.compare(getProfilePost().getLikes(),
                o.getProfilePost().getLikes());
    }
}