package com.liferay.mobile.screens.auth.login.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.forgotpassword.operation.ForgotPasswordOperation;
import com.liferay.mobile.screens.service.v62.ScreensuserService;

/**
 * @author Javier Gamarra
 */
public class ScreensUserOperation62 implements ForgotPasswordOperation, CurrentUserOperation {

	public ScreensUserOperation62(Session session) {
		_screensuserService = new ScreensuserService(session);
	}

	@Override
	public void sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
		_screensuserService.sendPasswordByEmailAddress(companyId, emailAddress);
	}

	public void sendPasswordByScreenName(long companyId, String screenName) throws Exception {
		_screensuserService.sendPasswordByScreenName(companyId, screenName);
	}

	@Override
	public void sendPasswordByUserId(long userId) throws Exception {
		_screensuserService.sendPasswordByUserId(userId);
	}

	@Override
	public void getCurrentUser() throws Exception {
		_screensuserService.getCurrentUser();
	}

	private ScreensuserService _screensuserService;
}
