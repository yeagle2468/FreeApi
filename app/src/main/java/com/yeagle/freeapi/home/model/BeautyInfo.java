package com.yeagle.freeapi.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yeagle.freeapi.photo.IPhotoInfo;

/**
 * Created by yeagle on 2018/5/2.
 */
public class BeautyInfo implements IPhotoInfo{
    public String createdAt;
    public String publishedAt;
    public String type;
    public String url;

    public BeautyInfo(){}

    public BeautyInfo(Parcel parcel) {
        this.createdAt = parcel.readString();
        this.publishedAt = parcel.readString();
        this.type = parcel.readString();
        this.url = parcel.readString();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.createdAt);
        parcel.writeString(this.publishedAt);
        parcel.writeString(this.type);
        parcel.writeString(this.url);
    }

    public static final Parcelable.Creator<BeautyInfo> CREATOR = new Parcelable.Creator<BeautyInfo>() {
        public BeautyInfo createFromParcel(Parcel source) {
            return new BeautyInfo(source);
        }

        public BeautyInfo[] newArray(int size) {
            return new BeautyInfo[size];
        }
    };
}
