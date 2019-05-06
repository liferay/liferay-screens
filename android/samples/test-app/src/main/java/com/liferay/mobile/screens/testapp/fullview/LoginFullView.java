package com.liferay.mobile.screens.testapp.fullview;

import android.content.Context;
import android.provider.Settings;
import android.util.AttributeSet;
import com.liferay.mobile.screens.testapp.R;
import com.liferay.mobile.screens.viewsets.defaultviews.auth.login.LoginView;

/**
 * @author Javier Gamarra
 */
public class LoginFullView extends LoginView {
    public LoginFullView(Context context) {
        super(context);
    }

    public LoginFullView(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    public LoginFullView(Context context, AttributeSet attributes, int defaultStyle) {
        super(context, attributes, defaultStyle);
    }

    @Override
    public String getPassword() {
        return Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    protected int getLoginEditTextDrawableId() {
        return R.drawable.material_account_box;
    }
}
