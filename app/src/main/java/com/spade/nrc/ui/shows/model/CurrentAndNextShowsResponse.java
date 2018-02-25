package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class CurrentAndNextShowsResponse implements Parcelable {

    @SerializedName("success")
    private boolean success;
    @SerializedName("data")
    private CurrentAndNextShowsData currentAndNextShowsData;


    public final static Parcelable.Creator<CurrentAndNextShowsResponse> CREATOR = new Parcelable.Creator<CurrentAndNextShowsResponse>() {

        @SuppressWarnings({"unchecked"})
        public CurrentAndNextShowsResponse createFromParcel(Parcel in) {
            return new CurrentAndNextShowsResponse(in);
        }

        public CurrentAndNextShowsResponse[] newArray(int size) {
            return (new CurrentAndNextShowsResponse[size]);
        }

    };

    protected CurrentAndNextShowsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.currentAndNextShowsData = ((CurrentAndNextShowsData) in.readValue((CurrentAndNextShowsData.class.getClassLoader())));
    }

    public CurrentAndNextShowsResponse() {
    }


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public CurrentAndNextShowsData getCurrentAndNextShowsData() {
        return currentAndNextShowsData;
    }

    public void setCurrentAndNextShowsData(CurrentAndNextShowsData currentAndNextShowsData) {
        this.currentAndNextShowsData = currentAndNextShowsData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(currentAndNextShowsData);
    }

    public int describeContents() {
        return 0;
    }
}
