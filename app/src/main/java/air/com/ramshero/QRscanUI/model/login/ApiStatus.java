package air.com.ramshero.QRscanUI.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class ApiStatus {

    @SerializedName("api")
    @Expose
    private Integer api;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getApi() {
        return api;
    }

    public void setApi(Integer api) {
        this.api = api;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
