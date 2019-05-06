package com.liferay.mobile.screens.auth.login.interactor;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsIntent;
import com.liferay.mobile.android.auth.OAuth2SignIn;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.screens.auth.login.LoginRedirectListener;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import java.util.List;

public class LoginOAuth2RedirectInteractor extends BaseLoginInteractor {

    @Override
    public BasicEvent execute(Object... args) throws Exception {

        String clientId = (String) args[0];
        List<String> scopes = (List<String>) args[1];
        String redirectUri = (String) args[2];
        CustomTabsIntent customTabsIntent = (CustomTabsIntent) args[3];
        Context context = (Context) args[4];

        Activity activity = LiferayScreensContext.getActivityFromContext(context);
        Session session = new SessionImpl(LiferayServerContext.getServer());

        validate(clientId, scopes, redirectUri);

        OAuth2SignIn.signInWithRedirect(activity, session, clientId, scopes, Uri.parse(redirectUri), customTabsIntent);

        return new BasicEvent();
    }

    private void validate(String clientId, List<String> scopes, String redirectUri) {
        if (clientId == null || clientId.isEmpty()) {
            throw new IllegalArgumentException("clientId cannot be null or empty");
        } else if (scopes == null) {
            throw new IllegalArgumentException("scopes cannot be null");
        } else if (redirectUri == null) {
            throw new IllegalArgumentException("redirectUri cannot be null");
        }
    }

    @Override
    public void onSuccess(BasicEvent event) {
        if (getListener() instanceof LoginRedirectListener) {
            ((LoginRedirectListener) getListener()).onAuthenticationBrowserShown();
        }
    }

    @Override
    public void onFailure(BasicEvent event) {
        getListener().onLoginFailure(event.getException());
    }
}
