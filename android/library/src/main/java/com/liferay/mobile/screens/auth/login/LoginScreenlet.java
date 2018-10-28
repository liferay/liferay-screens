/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.auth.login;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.support.customtabs.CustomTabsIntent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.android.auth.basic.CookieAuthentication;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.interactor.BaseLoginInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginBasicInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginCookieInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginOAuth2RedirectInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginOAuth2ResumeRedirectInteractor;
import com.liferay.mobile.screens.auth.login.interactor.LoginOAuth2UsernameAndPasswordInteractor;
import com.liferay.mobile.screens.auth.login.view.LoginViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType;

/**
 * @author Silvio Santos
 */
public class LoginScreenlet extends BaseScreenlet<LoginViewModel, BaseLoginInteractor>
    implements LoginListener, LoginRedirectListener {

    public static final String RESUME_REDIRECT_ACTION = "RESUME_REDIRECT_ACTION";
    public static final String BASIC_AUTH = "BASIC_AUTH";
    public static final String LOGIN_SUCCESSFUL = "com.liferay.mobile.screens.auth.login.success";
    private LoginListener listener;
    private BasicAuthMethod basicAuthMethod;
    private AuthenticationType authenticationType;
    private StorageType credentialsStorage;
    private boolean shouldHandleCookieExpiration;
    private int cookieExpirationTime;
    private List<String> oauth2Scopes;
    private String oauth2ClientId;
    private String oauth2ClientSecret;
    private String oauth2RedirectUrl;
    private CustomTabsIntent oauth2CustomTabsIntent;

    public LoginScreenlet(Context context) {
        super(context);
    }

    public LoginScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoginScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void resumeOAuth2RedirectFlow(Intent intent) {
        performUserAction(RESUME_REDIRECT_ACTION, intent);
    }

    @Override
    public void onLoginFailure(Exception e) {
        getViewModel().showFailedOperation(null, e);

        if (listener != null) {
            listener.onLoginFailure(e);
        }
    }

    @Override
    public void onAuthenticationBrowserShown() {
        if (listener != null) {
            getListener().onAuthenticationBrowserShown();
        }
    }

    @Override
    public void onLoginSuccess(User user) {
        getViewModel().showFinishOperation(user);

        if (listener != null) {
            listener.onLoginSuccess(user);
        }

        getContext().sendBroadcast(new Intent(LOGIN_SUCCESSFUL));

        SessionContext.removeStoredCredentials(credentialsStorage);
        SessionContext.storeCredentials(credentialsStorage);
    }

    public LoginListener getListener() {
        return listener;
    }

    public void setListener(LoginListener listener) {
        this.listener = listener;
    }

    public BasicAuthMethod getAuthMethod() {
        return basicAuthMethod;
    }

    public StorageType getCredentialsStorage() {
        return credentialsStorage;
    }

    public void setCredentialsStorage(StorageType value) {
        credentialsStorage = value;
    }

    public BasicAuthMethod getBasicAuthMethod() {
        return basicAuthMethod;
    }

    public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
        this.basicAuthMethod = basicAuthMethod;

        getViewModel().setBasicAuthMethod(this.basicAuthMethod);
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;

        getViewModel().setAuthenticationType(authenticationType);
    }

    public boolean isShouldHandleCookieRefresh() {
        return shouldHandleCookieExpiration;
    }

    public void setShouldHandleCookieExpiration(boolean shouldHandleCookieExpiration) {
        this.shouldHandleCookieExpiration = shouldHandleCookieExpiration;
    }

    public int getCookieExpirationTime() {
        return cookieExpirationTime;
    }

    public void setCookieExpirationTime(int cookieExpirationTime) {
        this.cookieExpirationTime = cookieExpirationTime;
    }

    public List<String> getOauth2Scopes() {
        return oauth2Scopes;
    }

    public void setOauth2Scopes(List<String> oauth2Scopes) {
        this.oauth2Scopes = oauth2Scopes;
    }

    public String getOauth2ClientId() {
        return oauth2ClientId;
    }

    public void setOauth2ClientId(String oauth2ClientId) {
        this.oauth2ClientId = oauth2ClientId;
    }

    public String getOauth2ClientSecret() {
        return oauth2ClientSecret;
    }

    public void setOauth2ClientSecret(String oauth2ClientSecret) {
        this.oauth2ClientSecret = oauth2ClientSecret;
    }

    public String getOauth2RedirectUrl() {
        return oauth2RedirectUrl;
    }

    public void setOauth2RedirectUrl(String oauth2RedirectUrl) {
        this.oauth2RedirectUrl = oauth2RedirectUrl;
    }

    public CustomTabsIntent getOauth2CustomTabsIntent() {
        return oauth2CustomTabsIntent;
    }

    public void setOauth2CustomTabsIntent(CustomTabsIntent oauth2CustomTabsIntent) {
        this.oauth2CustomTabsIntent = oauth2CustomTabsIntent;
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attributes, R.styleable.LoginScreenlet, 0, 0);

        int storeValue = typedArray.getInt(R.styleable.LoginScreenlet_credentialsStorage, StorageType.NONE.toInt());

        credentialsStorage = StorageType.valueOf(storeValue);

        shouldHandleCookieExpiration =
            typedArray.getBoolean(R.styleable.LoginScreenlet_shouldHandleCookieExpiration, true);
        cookieExpirationTime = typedArray.getInt(R.styleable.LoginScreenlet_cookieExpirationTime,
            CookieAuthentication.COOKIE_EXPIRATION_TIME);

        oauth2ClientId = typedArray.getString(R.styleable.LoginScreenlet_oauth2ClientId);
        oauth2ClientSecret = typedArray.getString(R.styleable.LoginScreenlet_oauth2ClientSecret);
        oauth2RedirectUrl = typedArray.getString(R.styleable.LoginScreenlet_oauth2Redirect);

        String scopesString = typedArray.getString(R.styleable.LoginScreenlet_oauth2Scopes);

        if (scopesString != null) {
            oauth2Scopes = Arrays.asList(scopesString.split(" "));
        } else {
            oauth2Scopes = new ArrayList<>();
        }

        int layoutId = typedArray.getResourceId(R.styleable.LoginScreenlet_layoutId, getDefaultLayoutId());

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        LoginViewModel loginViewModel = (LoginViewModel) view;

        int authMethodId = typedArray.getInt(R.styleable.LoginScreenlet_loginMode, 0);
        authenticationType = AuthenticationType.values()[authMethodId];

        loginViewModel.setAuthenticationType(authenticationType);

        if (AuthenticationType.BASIC.equals(authenticationType)
            || AuthenticationType.COOKIE.equals(authenticationType)
            || AuthenticationType.OAUTH2USERNAMEANDPASSWORD.equals(authenticationType)) {
            int basicAuthMethodId = typedArray.getInt(R.styleable.LoginScreenlet_basicAuthMethod, 0);

            basicAuthMethod = BasicAuthMethod.getValue(basicAuthMethodId);
            loginViewModel.setBasicAuthMethod(basicAuthMethod);
        }

        typedArray.recycle();

        return view;
    }

    @Override
    protected BaseLoginInteractor createInteractor(String actionName) {
        if (RESUME_REDIRECT_ACTION.equals(actionName)) {
            return new LoginOAuth2ResumeRedirectInteractor();
        }

        if (AuthenticationType.COOKIE.equals(authenticationType)) {
            return new LoginCookieInteractor();
        } else if (AuthenticationType.OAUTH2USERNAMEANDPASSWORD.equals(authenticationType)) {
            return new LoginOAuth2UsernameAndPasswordInteractor();
        } else if (AuthenticationType.OAUTH2REDIRECT.equals(authenticationType)) {
            return new LoginOAuth2RedirectInteractor();
        } else {
            return new LoginBasicInteractor();
        }
    }

    @Override
    protected void onUserAction(String userActionName, BaseLoginInteractor interactor, Object... args) {
        if (RESUME_REDIRECT_ACTION.equals(userActionName)) {
            interactor.start(args);
            return;
        }

        LoginViewModel viewModel = getViewModel();
        if (AuthenticationType.COOKIE.equals(authenticationType)) {
            interactor.start(viewModel.getLogin(), viewModel.getPassword(), shouldHandleCookieExpiration,
                cookieExpirationTime);
        } else if (AuthenticationType.BASIC.equals(authenticationType)) {
            interactor.start(viewModel.getLogin(), viewModel.getPassword(), viewModel.getBasicAuthMethod());
        } else if (AuthenticationType.OAUTH2USERNAMEANDPASSWORD.equals(authenticationType)) {
            interactor.start(viewModel.getLogin(), viewModel.getPassword(), oauth2ClientId, oauth2ClientSecret,
                oauth2Scopes);
        } else if (AuthenticationType.OAUTH2REDIRECT.equals(authenticationType)) {
            interactor.start(oauth2ClientId, oauth2Scopes, oauth2RedirectUrl, oauth2CustomTabsIntent, getContext());
        }
    }
}