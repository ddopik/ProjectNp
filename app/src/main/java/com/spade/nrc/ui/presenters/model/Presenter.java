
package com.spade.nrc.ui.presenters.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.spade.nrc.ui.shows.model.Show;

import java.util.List;

public class Presenter implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("show")
    @Expose
    private List<Show> showList;

    public final static Creator<Presenter> CREATOR = new Creator<Presenter>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Presenter createFromParcel(Parcel in) {
            return new Presenter(in);
        }

        public Presenter[] newArray(int size) {
            return (new Presenter[size]);
        }

    };

    protected Presenter(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.facebook = ((String) in.readValue((String.class.getClassLoader())));
        this.twitter = ((String) in.readValue((String.class.getClassLoader())));
        this.instagram = ((String) in.readValue((String.class.getClassLoader())));
        this.media = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.showList, Show.class.getClassLoader());
    }

    public Presenter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public List<Show> getShowList() {
        return showList;
    }

    public void setShowList(List<Show> showList) {
        this.showList = showList;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(slug);
        dest.writeValue(description);
        dest.writeValue(facebook);
        dest.writeValue(twitter);
        dest.writeValue(instagram);
        dest.writeValue(media);
        dest.writeList(showList);
    }

    public int describeContents() {
        return 0;
    }

}
