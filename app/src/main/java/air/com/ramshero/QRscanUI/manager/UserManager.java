package air.com.ramshero.QRscanUI.manager;

import android.content.Context;

public class UserManager {
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance() {
        if (ourInstance == null){
            ourInstance = new UserManager();
        }
        return ourInstance;
    }

    private String uId;
    private String uName;
    private String userPhoto;
    private String coverPhoto;
    private String levelId;

    private Context mContext;

    private UserManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
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
