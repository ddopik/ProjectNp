
package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowDetailsResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Show show;
    public final static Creator<ShowDetailsResponse> CREATOR = new Creator<ShowDetailsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShowDetailsResponse createFromParcel(Parcel in) {
            return new ShowDetailsResponse(in);
        }

        public ShowDetailsResponse[] newArray(int size) {
            return (new ShowDetailsResponse[size]);
        }

    };

    protected ShowDetailsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.show = (Show) in.readValue(Show.class.getClassLoader());
    }

    public ShowDetailsResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(show);
    }

    public int describeContents() {
        return 0;
    }

}
