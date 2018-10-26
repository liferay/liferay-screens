package com.liferay.mobile.screens.auth.login.interactor;

import android.content.Context;
import android.content.Intent;
import com.liferay.mobile.android.auth.OAuth2SignIn;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.login.connector.CurrentUserConnector;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONObject;

public class LoginOAuth2ResumeRedirectInteractor extends BaseLoginInteractor {

    @Override
    public BasicEvent execute(Object... args) throws Exception {

        Intent redirectIntent = (Intent) args[0];
        Context context = LiferayScreensContext.getContext();

        Session session = new SessionImpl(LiferayServerContext.getServer());

        validate(redirectIntent);

        Session oauth2Session = OAuth2SignIn.resumeAuthorizationFlowWithIntent(context, session, redirectIntent, null);

        SessionContext.createOAuth2Session(oauth2Session);
        CurrentUserConnector userConnector = getCurrentUserConnector(oauth2Session);

        JSONObject jsonObject = userConnector.getCurrentUser();

        return new BasicEvent(jsonObject);
    }

    private void validate(Intent redirectIntent) {
        if (redirectIntent == null) {
            throw new IllegalArgumentException("Redirect intent can't be null");
        } else if (redirectIntent.getData() == null) {
            throw new IllegalArgumentException("Redirect intent data can't be null");
        }
    }

    private CurrentUserConnector getCurrentUserConnector(Session session) {
        return ServiceProvider.getInstance().getCurrentUserConnector(session);
    }
}
