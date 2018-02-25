
package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShowsPagesResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    private ShowsData showsData;

    public final static Creator<ShowsPagesResponse> CREATOR = new Creator<ShowsPagesResponse>() {

        @SuppressWarnings({
                "unchecked"
        })
        public ShowsPagesResponse createFromParcel(Parcel in) {
            return new ShowsPagesResponse(in);
        }

        public ShowsPagesResponse[] newArray(int size) {
            return (new ShowsPagesResponse[size]);
        }

    };

    protected ShowsPagesResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.showsData = (ShowsData) in.readValue(ShowsData.class.getClassLoader());
    }

    public ShowsPagesResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public ShowsData getShowsData() {
        return showsData;
    }

    public void setShowsData(ShowsData showsData) {
        this.showsData = showsData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(showsData);
    }

    public int describeContents() {
        return 0;
    }

}
