package com.gabrielamihalachioaie.petagram;

import android.os.Parcel;
import android.os.Parcelable;

public class Mascota implements Parcelable {
    private final int pictureResourceId;
    private final String name;
    private int bones;

    public Mascota(int pictureResourceId, String name, int bones) {
        this.pictureResourceId = pictureResourceId;
        this.name = name;
        this.bones = bones;
    }

    protected Mascota(Parcel in) {
        pictureResourceId = in.readInt();
        name = in.readString();
        bones = in.readInt();
    }

    public static final Creator<Mascota> CREATOR = new Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel in) {
            return new Mascota(in);
        }

        @Override
        public Mascota[] newArray(int size) {
            return new Mascota[size];
        }
    };

    public int getPictureResourceId() {
        return pictureResourceId;
    }

    public String getName() {
        return name;
    }

    public int getBones() {
        return bones;
    }

    public void addBone() {
        bones += 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pictureResourceId);
        dest.writeString(name);
        dest.writeInt(bones);
    }
}
