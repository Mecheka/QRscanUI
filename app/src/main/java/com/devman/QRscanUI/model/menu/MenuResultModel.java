package com.devman.QRscanUI.model.menu;

import com.devman.QRscanUI.model.login.ApiStatus;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MenuResultModel {

    @SerializedName("menu")
    @Expose
    private ArrayList<MenuModel> menu = null;
    @SerializedName("api_status")
    @Expose
    private ApiStatus apiStatus;

    public ArrayList<MenuModel> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<MenuModel> menu) {
        this.menu = menu;
    }

    public ApiStatus getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(ApiStatus apiStatus) {
        this.apiStatus = apiStatus;
    }
}
