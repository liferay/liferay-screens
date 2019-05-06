package com.liferay.mobile.screens.auth.login.interactor;

import com.liferay.mobile.android.auth.CookieSignIn;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONObject;

/**
 * @author Víctor Galán Grande
 */

public class LoginCookieInteractor extends BaseLoginInteractor {

    @Override
    public BasicEvent execute(Object... args) throws Exception {

        String login = (String) args[0];
        String password = (String) args[1];
        boolean shouldHandleExpiration = (boolean) args[2];
        int cookieExpirationTime = (int) args[3];

        validate(login, password);

        CookieAuthentication authentication =
            new CookieAuthentication("", "", login, password, shouldHandleExpiration, cookieExpirationTime, 0);

        Session session = SessionContext.createBasicSession(login, password);
        session.setAuthentication(authentication);

        Session cookieSession = CookieSignIn.signIn(session);

        SessionContext.createCookieSession(cookieSession);
        CurrentUserConnector userConnector = getCurrentUserConnector(cookieSession);

        JSONObject jsonObject = userConnector.getCurrentUser();

        return new BasicEvent(jsonObject);
    }

    protected void validate(String login, String password) {
        if (login == null) {
            throw new IllegalArgumentException("Login cannot be empty");
        } else if (password == null) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
    }

    public CurrentUserConnector getCurrentUserConnector(Session session) {
        return ServiceProvider.getInstance().getCurrentUserConnector(session);
    }
}
