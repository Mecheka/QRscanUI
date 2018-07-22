package com.devman.QRscanUI.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.devman.QRscanUI.R;
import com.devman.QRscanUI.manager.UserManager;
import com.devman.QRscanUI.model.login.User;

public class WebResultScanActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgBgUser;
    private TextView textUser;
    private ImageView btnLogout;
    private ImageView btnBack;
    private WebView webView;

    private String url;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_result_scan);

        initData();
        initInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WebResultScanActivity.this, ScanQrActivity.class);
        startActivity(intent);
        finish();
    }

    private void initData() {
        url = getIntent().getStringExtra("urlResult");
    }

    private void initInstance() {

        imgBgUser = findViewById(R.id.imgBguser);
        textUser = findViewById(R.id.textUser);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        webView = findViewById(R.id.webView);

        btnLogout.setOnClickListener(this);
        btnBack.setOnClickListener(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        displayImage();

    }

    private void displayImage() {

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

            Glide.with(this)
                    .load(UserManager.getInstance().getCoverPhoto())
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .into(imgBgUser);

            textUser.setText(UserManager.getInstance().getuName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }
}
