package com.liferay.mobile.screens.testapp.fullview;

import android.os.Bundle;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.testapp.ThemeActivity;

/**
 * @author Javier Gamarra
 */
public class LoginFullActivity extends ThemeActivity {

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.login_full_layout);
    }
}
