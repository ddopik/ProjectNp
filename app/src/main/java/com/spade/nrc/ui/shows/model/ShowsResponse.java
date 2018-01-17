
package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShowsResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Show> data = null;
    public final static Creator<ShowsResponse> CREATOR = new Creator<ShowsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ShowsResponse createFromParcel(Parcel in) {
            return new ShowsResponse(in);
        }

        public ShowsResponse[] newArray(int size) {
            return (new ShowsResponse[size]);
        }

    };

    protected ShowsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.data, (Show.class.getClassLoader()));
    }

    public ShowsResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Show> getData() {
        return data;
    }

    public void setData(List<Show> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}
