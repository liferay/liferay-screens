package com.liferay.mobile.pushnotifications.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import com.liferay.mobile.pushnotifications.R;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;

public class LoginActivity extends AppCompatActivity implements LoginListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginScreenlet loginScreenlet = findViewById(R.id.login_screenlet);
        loginScreenlet.setListener(this);

        setDefaultValuesForUserAndPassword();
    }

    @Override
    public void onLoginSuccess(User user) {
        startActivity(new Intent(this, NotificationsActivity.class));
    }

    @Override
    public void onLoginFailure(Exception e) {

    }

    @Override
    public void onAuthenticationBrowserShown() {

    }

    private void setDefaultValuesForUserAndPassword() {
        EditText login = findViewById(R.id.liferay_login);
        login.setText(R.string.default_user);

        EditText password = findViewById(R.id.liferay_password);
        password.setText(R.string.default_password);
    }
}
