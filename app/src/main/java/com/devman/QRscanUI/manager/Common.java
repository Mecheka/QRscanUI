package com.devman.QRscanUI.manager;

public class Common {

    private static final String BASEURL = "http://devman.co.th/system/qr_scan_api/";

    public static IQRCodeAPI getAPI(){
        return RetrofitClient.getClient(BASEURL).create(IQRCodeAPI.class);
    }

}
