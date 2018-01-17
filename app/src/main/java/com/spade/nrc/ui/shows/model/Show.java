
package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Show implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("is_featured")
    @Expose
    private Boolean isFeatured;
    @SerializedName("presenters")
    @Expose
    private List<Presenter> presenters = null;
    @SerializedName("schedules")
    @Expose
    private List<Schedule> schedules = null;
    @SerializedName("channel")
    @Expose
    private Channel channel;
    public final static Creator<Show> CREATOR = new Creator<Show>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Show createFromParcel(Parcel in) {
            return new Show(in);
        }

        public Show[] newArray(int size) {
            return (new Show[size]);
        }

    };

    protected Show(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.media = ((String) in.readValue((String.class.getClassLoader())));
        this.isFeatured = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.presenters, (com.spade.nrc.ui.shows.model.Presenter.class.getClassLoader()));
        in.readList(this.schedules, (com.spade.nrc.ui.shows.model.Schedule.class.getClassLoader()));
        this.channel = (Channel) in.readValue(Channel.class.getClassLoader());
    }

    public Show() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public List<Presenter> getPresenters() {
        return presenters;
    }

    public void setPresenters(List<Presenter> presenters) {
        this.presenters = presenters;
    }

    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }

    public Boolean getFeatured() {
        return isFeatured;
    }

    public void setFeatured(Boolean featured) {
        isFeatured = featured;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(slug);
        dest.writeValue(description);
        dest.writeValue(media);
        dest.writeValue(isFeatured);
        dest.writeList(presenters);
        dest.writeList(schedules);
        dest.writeValue(channel);
    }

    public int describeContents() {
        return 0;
    }

}
