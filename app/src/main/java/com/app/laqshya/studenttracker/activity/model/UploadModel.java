package com.app.laqshya.studenttracker.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadModel implements Parcelable {
    public static final Creator<UploadModel> CREATOR = new Creator<UploadModel>() {
        @Override
        public UploadModel createFromParcel(Parcel in) {
            return new UploadModel(in);
        }

        @Override
        public UploadModel[] newArray(int size) {
            return new UploadModel[size];
        }
    };





    private UploadModel(Parcel in) {
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
