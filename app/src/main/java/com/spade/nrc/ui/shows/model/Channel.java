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
    private String brief;
    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("instgram")
    @Expose
    private String instgram;
    @SerializedName("youtube")
    @Expose
    private String youtube;
    @SerializedName("telephone_number")
    @Expose
    private String telephoneNumber;
    @SerializedName("sms_number")
    @Expose
    private String smsNumber;
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
        this.brief = ((String) in.readValue((String.class.getClassLoader())));
        this.media = ((String) in.readValue((String.class.getClassLoader())));
        this.facebook = ((String) in.readValue((String.class.getClassLoader())));
        this.twitter = ((String) in.readValue((String.class.getClassLoader())));
        this.instgram = ((String) in.readValue((String.class.getClassLoader())));
        this.youtube = ((String) in.readValue((String.class.getClassLoader())));
        this.telephoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.smsNumber = ((String) in.readValue((String.class.getClassLoader())));

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

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    public String getInstgram() {
        return instgram;
    }

    public void setInstgram(String instgram) {
        this.instgram = instgram;
    }


    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getSmsNumber() {
        return smsNumber;
    }

    public void setSmsNumber(String smsNumber) {
        this.smsNumber = smsNumber;
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
        dest.writeValue(telephoneNumber);
        dest.writeValue(smsNumber);
    }

    public int describeContents() {
        return 0;
    }

}
