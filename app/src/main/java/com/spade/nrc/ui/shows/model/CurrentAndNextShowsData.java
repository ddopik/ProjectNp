package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 1/31/18.
 */

public class CurrentAndNextShowsData implements Parcelable {

    @SerializedName("current_show")
    private Show currentShow;
    @SerializedName("next_show")
    private Show nextShow;

    public final static Parcelable.Creator<CurrentAndNextShowsData> CREATOR = new Parcelable.Creator<CurrentAndNextShowsData>() {


        @SuppressWarnings({"unchecked"})
        public CurrentAndNextShowsData createFromParcel(Parcel in) {
            return new CurrentAndNextShowsData(in);
        }

        public CurrentAndNextShowsData[] newArray(int size) {
            return (new CurrentAndNextShowsData[size]);
        }

    };

    protected CurrentAndNextShowsData(Parcel in) {
        this.currentShow = ((Show) in.readValue((Show.class.getClassLoader())));
        this.nextShow = ((Show) in.readValue((Show.class.getClassLoader())));
    }

    public CurrentAndNextShowsData() {
    }

    public Show getCurrentShow() {
        return currentShow;
    }

    public void setCurrentShow(Show currentShow) {
        this.currentShow = currentShow;
    }

    public Show getNextShow() {
        return nextShow;
    }

    public void setNextShow(Show nextShow) {
        this.nextShow = nextShow;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(currentShow);
        dest.writeValue(nextShow);
    }

    public int describeContents() {
        return 0;
    }
}
