package com.liferay.mobile.screens.auth.login.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.forgotpassword.connector.ForgotPasswordConnector;
import com.liferay.mobile.screens.service.v62.ScreensuserService;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class ScreensUserConnector62 implements ForgotPasswordConnector, CurrentUserConnector {

    private final ScreensuserService screensUserService;

    public ScreensUserConnector62(Session session) {
        screensUserService = new ScreensuserService(session);
    }

    @Override
    public Boolean sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
        return screensUserService.sendPasswordByEmailAddress(companyId, emailAddress);
    }

    public Boolean sendPasswordByScreenName(long companyId, String screenName) throws Exception {
        return screensUserService.sendPasswordByScreenName(companyId, screenName);
    }

    @Override
    public Boolean sendPasswordByUserId(long userId) throws Exception {
        return screensUserService.sendPasswordByUserId(userId);
    }

    @Override
    public JSONObject getCurrentUser() throws Exception {
        return screensUserService.getCurrentUser();
    }
}
