package com.devman.QRscanUI.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.devman.QRscanUI.R;
import com.devman.QRscanUI.activity.MainActivity;
import com.devman.QRscanUI.manager.Common;
import com.devman.QRscanUI.manager.IQRCodeAPI;
import com.devman.QRscanUI.manager.UserManager;
import com.devman.QRscanUI.model.login.LoginResultModel;

import org.parceler.Parcels;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String SPF_LOGIN = "loginperferences";
    private final String USER = "user";
    private final String PASS = "pass";

    private EditText editUser;
    private EditText editPass;
    private CheckBox checkRemember;
    private Button btnLogin;
    private IQRCodeAPI mService;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mService = Common.getAPI();

        SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN,
                Context.MODE_PRIVATE);
        String user = loginPreferences.getString(USER, null);
        String pass = loginPreferences.getString(PASS, null);


        if (user != null && pass != null) {
            autoLoginUser(user, pass);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        initInstance(rootView);
        return rootView;
    }

    private void initInstance(View rootView) {

        editUser = rootView.findViewById(R.id.editUser);
        editPass = rootView.findViewById(R.id.editPass);
        checkRemember = rootView.findViewById(R.id.checkRememberMe);
        btnLogin = rootView.findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            loginUser();
        }
    }

    private void loginUser() {

        final String user = editUser.getText().toString();
        final String pass = editPass.getText().toString();

        mService.loginRequest(user, pass)
                .enqueue(new Callback<LoginResultModel>() {
                    @Override
                    public void onResponse(Call<LoginResultModel> call, Response<LoginResultModel> response) {

                        if (response.isSuccessful()) {
                            if (checkRemember.isChecked()) {
                                SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
                                loginPreferences.edit().putString(USER, user).putString(PASS, pass).apply();
                            } else {
                                SharedPreferences loginPreferences = getActivity().getSharedPreferences(SPF_LOGIN, Context.MODE_PRIVATE);
                                loginPreferences.edit().clear().apply();
                            }
                            LoginResultModel loginResult = response.body();
                            if (loginResult.getUser().getUId() == null) {
                                Toast.makeText(getActivity(), "ชื่อผู้ใช้หรือรหัสผิด", Toast.LENGTH_SHORT).show();
                            } else {
                                UserManager.getInstance().setCoverPhoto(loginResult.getUser().getCoverPhoto());
                                UserManager.getInstance().setuName(loginResult.getUser().getUName());
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.putExtra("user", Parcels.wrap(loginResult.getUser()));
                                startActivity(intent);
                                getActivity().finish();
                            }
                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<LoginResultModel> call, Throwable t) {
                        Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void autoLoginUser(String user, String pass) {

        mService.loginRequest(user, pass)
                .enqueue(new Callback<LoginResultModel>() {
                    @Override
                    public void onResponse(Call<LoginResultModel> call, Response<LoginResultModel> response) {

                        if (response.isSuccessful()) {
                            LoginResultModel loginResult = response.body();
                            UserManager.getInstance().setCoverPhoto(loginResult.getUser().getCoverPhoto());
                            UserManager.getInstance().setuName(loginResult.getUser().getUName());
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.putExtra("user", Parcels.wrap(loginResult.getUser()));
                            startActivity(intent);
                            getActivity().finish();
                        } else {
                            try {
                                Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }


                    @Override
                    public void onFailure(Call<LoginResultModel> call, Throwable t) {

                    }
                });
    }

}
