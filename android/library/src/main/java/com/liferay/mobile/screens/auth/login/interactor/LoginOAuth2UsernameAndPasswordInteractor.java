package com.liferay.mobile.screens.auth.login.interactor;

import com.liferay.mobile.android.auth.OAuth2SignIn;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.List;
import org.json.JSONObject;

public class LoginOAuth2UsernameAndPasswordInteractor extends BaseLoginInteractor {

    @Override
    public BasicEvent execute(Object... args) throws Exception {
        String username = (String) args[0];
        String password = (String) args[1];
        String clientId = (String) args[2];
        String clientSecret = (String) args[3];
        List<String> scopes = (List<String>) args[4];

        validate(username, password, clientId, clientSecret, scopes);

        Session session = new SessionImpl(LiferayServerContext.getServer());

        Session oauth2Session =
            OAuth2SignIn.signInWithUsernameAndPassword(username, password, session, clientId, clientSecret, scopes,
                null);

        SessionContext.createOAuth2Session(oauth2Session);
        CurrentUserConnector userConnector = getCurrentUserConnector(oauth2Session);

        JSONObject jsonObject = userConnector.getCurrentUser();

        return new BasicEvent(jsonObject);
    }

    protected void validate(String username, String password, String clientId, String clientSecret,
        List<String> scopes) {

        validateNonNullOrEmptyString("username", username);
        validateNonNullOrEmptyString("password", password);
        validateNonNullOrEmptyString("clientId", clientId);
        validateNonNullOrEmptyString("clientSecret", clientSecret);

        if (scopes == null) {
            throw new IllegalArgumentException("Scopes cannot be null");
        }
    }

    protected void validateNonNullOrEmptyString(String name, String attribute) {
        if (attribute == null || attribute.isEmpty()) {
            throw new IllegalArgumentException(name + " cannot be null or empty");
        }
    }

    public CurrentUserConnector getCurrentUserConnector(Session session) {
        return ServiceProvider.getInstance().getCurrentUserConnector(session);
    }
}
