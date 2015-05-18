package com.liferay.mobile.screens.testapp.fullview;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.interactor.LoginInteractor;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class LoginFullScreenlet extends LoginScreenlet {
	public LoginFullScreenlet(Context context) {
		super(context);
	}

	public LoginFullScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public LoginFullScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected void onUserAction(String userActionName, LoginInteractor interactor, Object... args) {
		LiferayLogger.e("logging call to interactor: " + userActionName);
		super.onUserAction(userActionName, interactor, args);
	}

}
