
package com.spade.nrc.ui.presenters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresenterDetailsResponse implements Parcelable {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Presenter presenter;
    public final static Creator<PresenterDetailsResponse> CREATOR = new Creator<PresenterDetailsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PresenterDetailsResponse createFromParcel(Parcel in) {
            return new PresenterDetailsResponse(in);
        }

        public PresenterDetailsResponse[] newArray(int size) {
            return (new PresenterDetailsResponse[size]);
        }

    };

    protected PresenterDetailsResponse(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.presenter = (Presenter) in.readValue(Presenter.class.getClassLoader());
    }

    public PresenterDetailsResponse() {
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Presenter getPresenter() {
        return presenter;
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(success);
        dest.writeValue(presenter);
    }

    public int describeContents() {
        return 0;
    }

}
