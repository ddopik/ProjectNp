package com.spade.nrc.ui.channel.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.shows.model.Channel;
import com.spade.nrc.ui.shows.model.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman Abouzeid on 2/5/18.
 */

public class ChannelsResponse implements Parcelable {


    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Channel> data = new ArrayList<>();
    public final static Parcelable.Creator<ChannelsResponse> CREATOR = new Parcelable.Creator<ChannelsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ChannelsResponse createFromParcel(Parcel in) {
            return new ChannelsResponse(in);
        }

        public ChannelsResponse[] newArray(int size) {
            return (new ChannelsResponse[size]);
        }

    };

    protected ChannelsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.data, (Show.class.getClassLoader()));
    }

    public ChannelsResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Channel> getData() {
        return data;
    }

    public void setData(List<Channel> data) {
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
