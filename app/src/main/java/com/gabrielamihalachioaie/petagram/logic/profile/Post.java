package com.gabrielamihalachioaie.petagram.logic.profile;

import android.os.Parcel;
import android.os.Parcelable;

public class Post implements Parcelable {
    private final int id;
    private final int pictureResourceId;
    private int likes;

    public Post(int id, int pictureResourceId, int likes) {
        this.id = id;
        this.pictureResourceId = pictureResourceId;
        this.likes = likes;
    }

    protected Post(Parcel in) {
        id = in.readInt();
        pictureResourceId = in.readInt();
        likes = in.readInt();
    }

    public int getId() {
        return id;
    }

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public int getLikes() {
        return likes;
    }

    public void addLike() {
        likes += 1;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(pictureResourceId);
        dest.writeInt(likes);
    }
}