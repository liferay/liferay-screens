package com.liferay.mobile.screens;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.sites.UserSiteViewModel;

/**
 * @author Javier Gamarra
 */
public class UserSitesScreenletView extends ListView implements UserSiteViewModel {

	public UserSitesScreenletView(Context context) {
		super(context);
	}

	public UserSitesScreenletView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	@Override
	public void showStartOperation(String actionName) {
	}

	@Override
	public void showFinishOperation(String actionName) {

	}

	@Override
	public void showFailedOperation(String actionName, Exception e) {

	}

	@Override
	public BaseScreenlet getScreenlet() {
		return _screenlet;
	}

	@Override
	public void setScreenlet(BaseScreenlet screenlet) {
		_screenlet = screenlet;
	}

	private BaseScreenlet _screenlet;
}
