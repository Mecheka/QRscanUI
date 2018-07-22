package com.devman.QRscanUI.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class User {

    @SerializedName("u_id")
    @Expose
    private String uId;
    @SerializedName("u_name")
    @Expose
    private String uName;
    @SerializedName("user_photo")
    @Expose
    private String userPhoto;
    @SerializedName("cover_photo")
    @Expose
    private String coverPhoto;
    @SerializedName("level_id")
    @Expose
    private String levelId;

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getUName() {
        return uName;
    }

    public void setUName(String uName) {
        this.uName = uName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(String coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

}
