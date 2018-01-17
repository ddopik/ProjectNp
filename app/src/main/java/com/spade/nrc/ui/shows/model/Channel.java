package com.spade.nrc.ui.shows.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Ayman Abouzeid on 1/11/18.
 */

public class Channel implements Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("brief")
    @Expose
    private Object brief;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("facebook")
    @Expose
    private Object facebook;
    @SerializedName("twitter")
    @Expose
    private Object twitter;
    @SerializedName("instgram")
    @Expose
    private Object instgram;
    @SerializedName("youtube")
    @Expose
    private Object youtube;
    public final static Parcelable.Creator<Channel> CREATOR = new Creator<Channel>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Channel createFromParcel(Parcel in) {
            return new Channel(in);
        }

        public Channel[] newArray(int size) {
            return (new Channel[size]);
        }

    };

    protected Channel(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.slug = ((String) in.readValue((String.class.getClassLoader())));
        this.brief = ((Object) in.readValue((Object.class.getClassLoader())));
        this.media = ((String) in.readValue((String.class.getClassLoader())));
        this.facebook = ((Object) in.readValue((Object.class.getClassLoader())));
        this.twitter = ((Object) in.readValue((Object.class.getClassLoader())));
        this.instgram = ((Object) in.readValue((Object.class.getClassLoader())));
        this.youtube = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public Channel() {
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

    public Object getBrief() {
        return brief;
    }

    public void setBrief(Object brief) {
        this.brief = brief;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Object getFacebook() {
        return facebook;
    }

    public void setFacebook(Object facebook) {
        this.facebook = facebook;
    }

    public Object getTwitter() {
        return twitter;
    }

    public void setTwitter(Object twitter) {
        this.twitter = twitter;
    }

    public Object getInstgram() {
        return instgram;
    }

    public void setInstgram(Object instgram) {
        this.instgram = instgram;
    }

    public Object getYoutube() {
        return youtube;
    }

    public void setYoutube(Object youtube) {
        this.youtube = youtube;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(slug);
        dest.writeValue(brief);
        dest.writeValue(media);
        dest.writeValue(facebook);
        dest.writeValue(twitter);
        dest.writeValue(instgram);
        dest.writeValue(youtube);
    }

    public int describeContents() {
        return 0;
    }

}
