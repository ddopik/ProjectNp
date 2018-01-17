
package com.spade.nrc.ui.presenters.model;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PresenterData implements Parcelable {

    @SerializedName("presenters")
    @Expose
    private List<Presenter> presenters = null;
    public final static Creator<PresenterData> CREATOR = new Creator<PresenterData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PresenterData createFromParcel(Parcel in) {
            return new PresenterData(in);
        }

        public PresenterData[] newArray(int size) {
            return (new PresenterData[size]);
        }

    };

    protected PresenterData(Parcel in) {
        in.readList(this.presenters, (com.spade.nrc.ui.presenters.model.Presenter.class.getClassLoader()));
    }

    public PresenterData() {
    }

    public List<Presenter> getPresenters() {
        return presenters;
    }

    public void setPresenters(List<Presenter> presenters) {
        this.presenters = presenters;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(presenters);
    }

    public int describeContents() {
        return 0;
    }

}
