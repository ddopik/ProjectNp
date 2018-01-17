
package com.spade.nrc.ui.presenters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresentersResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private PresenterData presenterData;
    public final static Creator<PresentersResponse> CREATOR = new Creator<PresentersResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PresentersResponse createFromParcel(Parcel in) {
            return new PresentersResponse(in);
        }

        public PresentersResponse[] newArray(int size) {
            return (new PresentersResponse[size]);
        }

    };

    protected PresentersResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.presenterData = ((PresenterData) in.readValue((PresenterData.class.getClassLoader())));
    }

    public PresentersResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public PresenterData getPresenterData() {
        return presenterData;
    }

    public void setPresenterData(PresenterData presenterData) {
        this.presenterData = presenterData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(presenterData);
    }

    public int describeContents() {
        return 0;
    }

}
