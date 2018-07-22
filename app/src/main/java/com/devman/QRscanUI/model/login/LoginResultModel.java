package com.devman.QRscanUI.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class LoginResultModel {

    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("api_status")
    @Expose
    private ApiStatus apiStatus;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }


}
