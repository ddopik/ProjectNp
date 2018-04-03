
package com.spade.nrc.ui.presenters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PresenterData implements Parcelable {

    @SerializedName("presenters")
    @Expose
    private List<Presenter> presenters = null;
    @Expose
    @SerializedName("last_page")
    private int lastPage;

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
        this.lastPage = ((Integer) in.readValue((Integer.class.getClassLoader())));

    }

    public PresenterData() {
    }

    public List<Presenter> getPresenters() {
        return presenters;
    }

    public void setPresenters(List<Presenter> presenters) {
        this.presenters = presenters;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(presenters);
        dest.writeInt(lastPage);
    }

    public int describeContents() {
        return 0;
    }

}
