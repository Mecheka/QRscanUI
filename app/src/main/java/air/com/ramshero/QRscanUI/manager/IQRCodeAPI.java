package air.com.ramshero.QRscanUI.manager;

import air.com.ramshero.QRscanUI.model.login.LoginResultModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IQRCodeAPI {

    @FormUrlEncoded
    @POST("user_login.php?token=devman&version=1")
    Call<LoginResultModel> loginRequest(@Field("user") String user,
                                        @Field("pass") String pass);

}
