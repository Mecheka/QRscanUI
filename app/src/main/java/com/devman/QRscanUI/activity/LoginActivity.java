package com.devman.QRscanUI.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.devman.QRscanUI.R;
import com.devman.QRscanUI.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, new LoginFragment())
                    .commit();
        }

    }
}
