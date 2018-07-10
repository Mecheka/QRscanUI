package air.com.ramshero.QRscanUI.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import air.com.ramshero.QRscanUI.fragment.LoginFragment;
import air.com.ramshero.QRscanUI.R;

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
