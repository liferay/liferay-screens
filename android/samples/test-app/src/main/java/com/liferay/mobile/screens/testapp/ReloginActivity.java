package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;

/**
 * @author Javier Gamarra
 */
public class ReloginActivity extends ThemeActivity implements LoginListener, View.OnClickListener {

    private TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relogin);

        if (SessionContext.isLoggedIn()) {
            userName = findViewById(R.id.user_name);
            userName.setText(SessionContext.getCurrentUser().getLastName());
        }

        findViewById(R.id.update_user).setOnClickListener(this);
        findViewById(R.id.relogin_button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.update_user) {
            change();
        } else {
            relogin();
        }
    }

    public void relogin() {
        SessionContext.relogin(this);
    }

    @Override
    public void onLoginSuccess(final User user) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                userName.setText(user.getLastName());
                info(getString(R.string.relogin_info));
            }
        });
    }

    @Override
    public void onLoginFailure(Exception e) {
        error(getString(R.string.relogin_error), e);
    }

    @Override
    public void onAuthenticationBrowserShown() {

    }

    public void change() {
        if (SessionContext.isLoggedIn()) {
            final User user = SessionContext.getCurrentUser();
            user.getValues().put("lastName", "EXAMPLE_LASTNAME");
            userName.setText(user.getLastName());
        }
    }
}
