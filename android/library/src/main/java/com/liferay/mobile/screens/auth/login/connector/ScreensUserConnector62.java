package com.liferay.mobile.screens.auth.login.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.service.v62.ScreensuserService;

/**
 * @author Javier Gamarra
 */
public class ScreensUserConnector62 implements ForgotPasswordConnector, CurrentUserConnector {

	public ScreensUserConnector62(Session session) {
		_screensuserService = new ScreensuserService(session);
	}

	@Override
	public Boolean sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
		return _screensuserService.sendPasswordByEmailAddress(companyId, emailAddress);
	}

	public Boolean sendPasswordByScreenName(long companyId, String screenName) throws Exception {
		return _screensuserService.sendPasswordByScreenName(companyId, screenName);
	}

	@Override
	public Boolean sendPasswordByUserId(long userId) throws Exception {
		return _screensuserService.sendPasswordByUserId(userId);
	}

	@Override
	public void getCurrentUser() throws Exception {
		_screensuserService.getCurrentUser();
	}

	private ScreensuserService _screensuserService;
}
