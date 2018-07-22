package com.devman.QRscanUI.manager;

import com.devman.QRscanUI.model.login.LoginResultModel;
import com.devman.QRscanUI.model.menu.MenuResultModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IQRCodeAPI {

    @FormUrlEncoded
    @POST("user_login.php?token=devman&version=1")
    Call<LoginResultModel> loginRequest(@Field("user") String user,
                                        @Field("pass") String pass);

    @FormUrlEncoded
    @POST("load_user_menu.php?token=devman&version=1")
    Call<MenuResultModel> getMenuResult(@Field("u_id") String u_id);
}
