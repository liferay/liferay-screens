package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.user.GetUserListener;
import com.liferay.mobile.screens.user.GetUserScreenlet;

/**
 * @author Sarai Díaz García
 */
public class GetUserActivity extends ThemeActivity implements GetUserListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_user);

        GetUserScreenlet screenlet = findViewById(R.id.get_user_screenlet);
        screenlet.setListener(this);
    }

    @Override
    public void onGetUserSuccess(User user) {
        info("User received! -> " + user.getEmail());
    }

    @Override
    public void onGetUserFailure(Exception exception) {
        error("Could not receive user", exception);
    }
}
