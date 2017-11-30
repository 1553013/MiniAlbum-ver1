package com.example.huynhxuankhanh.minialbum.gallery;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HUYNHXUANKHANH on 11/17/2017.
 */

public class InfoImage implements Parcelable {
    public static final Creator<InfoImage> CREATOR = new Creator<InfoImage>() {
        @Override
        public InfoImage createFromParcel(Parcel in) {
            return new InfoImage(in);
        }

        @Override
        public InfoImage[] newArray(int size) {
            return new InfoImage[size];
        }
    };
    private int iD;
    private long Size;
    private String pathFile, nameFile, nameBucket, dateTaken;

    public InfoImage(int iD, long size, String pathFile, String nameFile, String nameBucket, String dateTaken) {
        this.iD = iD;
        Size = size;
        this.pathFile = pathFile;
        this.nameFile = nameFile;
        this.nameBucket = nameBucket;
        this.dateTaken = dateTaken;
    }

    protected InfoImage(Parcel in) {
        iD = in.readInt();
        Size = in.readLong();
        pathFile = in.readString();
        nameFile = in.readString();
        nameBucket = in.readString();
        dateTaken = in.readString();
    }

    public long getSize() {
        return Size;
    }

    public String getPathFile() {
        return pathFile;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public String getNameBucket() {
        return nameBucket;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(iD);
        parcel.writeLong(Size);
        parcel.writeString(pathFile);
        parcel.writeString(nameFile);
        parcel.writeString(nameBucket);
        parcel.writeString(dateTaken);
    }
}
