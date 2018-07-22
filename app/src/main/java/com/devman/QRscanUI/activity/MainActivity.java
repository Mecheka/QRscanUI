package com.devman.QRscanUI.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.devman.QRscanUI.R;
import com.devman.QRscanUI.fragment.MainFragment;
import com.devman.QRscanUI.model.login.User;
import com.devman.QRscanUI.view.IClickFragment;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IClickFragment {

    private final String SPF_LOGIN = "loginperferences";

    private ImageView imgBgUser;
    private TextView textUser;
    private ImageView btnLogout;
    private ImageView btnBack;
    private ImageView btnBackToScan;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initInstance();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance(user))
                    .commit();
        }

    }

    private void initData() {
        user = Parcels.unwrap(getIntent().getParcelableExtra("user"));
    }

    private void initInstance() {

        imgBgUser = findViewById(R.id.imgBguser);
        textUser = findViewById(R.id.textUser);
        btnLogout = findViewById(R.id.btnLogout);
        btnBack = findViewById(R.id.btnBack);
        btnBackToScan = findViewById(R.id.btnBackToScan);

        btnLogout.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnBackToScan.setOnClickListener(this);

        displayImage();
    }

    @SuppressLint("CheckResult")
    private void displayImage() {

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this)
                .load(user.getCoverPhoto())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(imgBgUser);

        textUser.setText(user.getUName());

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogout:
                SharedPreferences loginPregerences = getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
                loginPregerences.edit().clear().apply();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnBackToScan:
                Intent intent1 = new Intent(MainActivity.this, ScanQrActivity.class);
                startActivity(intent1);
                getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onClickFragment(int clicked) {
        switch (clicked){
            case 0:
                btnBackToScan.setVisibility(View.GONE);
                btnBack.setVisibility(View.GONE);
                break;
            case 1:
                btnBack.setVisibility(View.VISIBLE);
                btnBackToScan.setVisibility(View.GONE);
                break;
            case 2:
                btnBack.setVisibility(View.GONE);
                btnBackToScan.setVisibility(View.VISIBLE);
        }
    }
}
