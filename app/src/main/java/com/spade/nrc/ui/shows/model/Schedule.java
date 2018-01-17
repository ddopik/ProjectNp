
package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Schedule implements Parcelable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("starts_at")
    @Expose
    private String startsAt;
    @SerializedName("ends_at")
    @Expose
    private String endsAt;
    public final static Creator<Schedule> CREATOR = new Creator<Schedule>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Schedule createFromParcel(Parcel in) {
            return new Schedule(in);
        }

        public Schedule[] newArray(int size) {
            return (new Schedule[size]);
        }

    };

    protected Schedule(Parcel in) {
        this.day = ((String) in.readValue((String.class.getClassLoader())));
        this.startsAt = ((String) in.readValue((String.class.getClassLoader())));
        this.endsAt = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Schedule() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = startsAt;
    }

    public String getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(String endsAt) {
        this.endsAt = endsAt;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(startsAt);
        dest.writeValue(endsAt);
    }

    public int describeContents() {
        return 0;
    }

}
