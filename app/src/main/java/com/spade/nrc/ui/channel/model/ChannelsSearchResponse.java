package com.spade.nrc.ui.channel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 2/5/18.
 */

public class ChannelsSearchResponse implements Parcelable {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private ChannelsDataResponse data = new ChannelsDataResponse();
    public final static Creator<ChannelsSearchResponse> CREATOR = new Creator<ChannelsSearchResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChannelsSearchResponse createFromParcel(Parcel in) {
            return new ChannelsSearchResponse(in);
        }

        public ChannelsSearchResponse[] newArray(int size) {
            return (new ChannelsSearchResponse[size]);
        }

    };

    protected ChannelsSearchResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.data = ((ChannelsDataResponse) in.readValue((ChannelsDataResponse.class.getClassLoader())));
    }

    public ChannelsSearchResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public ChannelsDataResponse getData() {
        return data;
    }

    public void setData(ChannelsDataResponse data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}
