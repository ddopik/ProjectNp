
package com.spade.nrc.ui.explore.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

public class LiveShowsResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<LiveShowsData> data = null;
    public final static Creator<LiveShowsResponse> CREATOR = new Creator<LiveShowsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public LiveShowsResponse createFromParcel(Parcel in) {
            return new LiveShowsResponse(in);
        }

        public LiveShowsResponse[] newArray(int size) {
            return (new LiveShowsResponse[size]);
        }

    };

    protected LiveShowsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.data, (LiveShowsData.class.getClassLoader()));
    }

    public LiveShowsResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<LiveShowsData> getData() {
        return data;
    }

    public void setData(List<LiveShowsData> data) {
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
