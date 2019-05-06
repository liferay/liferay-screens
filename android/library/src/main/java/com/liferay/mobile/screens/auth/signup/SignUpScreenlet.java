/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.auth.signup;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.signup.interactor.SignUpInteractor;
import com.liferay.mobile.screens.auth.signup.view.SignUpViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder.StorageType;
import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class SignUpScreenlet extends BaseScreenlet<SignUpViewModel, SignUpInteractor> implements SignUpListener {

    private String anonymousApiPassword;
    private String anonymousApiUserName;
    private boolean autoLogin;
    private long companyId;
    private StorageType credentialsStorage;
    private BasicAuthMethod basicAuthMethod;
    private SignUpListener listener;
    private LoginListener autoLoginListener;

    public SignUpScreenlet(Context context) {
        super(context);
    }

    public SignUpScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SignUpScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SignUpScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onSignUpFailure(Exception e) {
        getViewModel().showFailedOperation(null, e);

        if (getListener() != null) {
            getListener().onSignUpFailure(e);
        }
    }

    @Override
    public void onSignUpSuccess(User user) {
        getViewModel().showFinishOperation(user);

        if (autoLogin) {
            SignUpViewModel viewModel = getViewModel();

            String authUsername = getAuthUsernameFromUser(user);
            String password = viewModel.getPassword();

            SessionContext.createBasicSession(authUsername, password);
            SessionContext.setCurrentUser(user);

            if (autoLoginListener != null) {
                autoLoginListener.onLoginSuccess(user);
            }

            SessionContext.storeCredentials(credentialsStorage);
        }

        if (getListener() != null) {
            getListener().onSignUpSuccess(user);
        }
    }

    /**
     * Returns the username depending on its basic auth method.
     *
     * @return screen name, userId or email depending on the chosen
     * basic auth method.
     */
    public String getAuthUsernameFromUser(User user) {
        switch (basicAuthMethod) {
            case SCREEN_NAME:
                return user.getScreenName();
            case USER_ID:
                return String.valueOf(user.getId());
            case EMAIL:
            default:
                return user.getEmail();
        }
    }

    public String getAnonymousApiPassword() {
        return anonymousApiPassword;
    }

    public void setAnonymousApiPassword(String value) {
        anonymousApiPassword = value;
    }

    public String getAnonymousApiUserName() {
        return anonymousApiUserName;
    }

    public void setAnonymousApiUserName(String value) {
        anonymousApiUserName = value;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean value) {
        autoLogin = value;
    }

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long value) {
        companyId = value;
    }

    public SignUpListener getListener() {
        return listener;
    }

    public void setListener(SignUpListener value) {
        listener = value;
    }

    public LoginListener getAutoLoginListener() {
        return autoLoginListener;
    }

    public void setAutoLoginListener(LoginListener value) {
        autoLoginListener = value;
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
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.SignUpScreenlet, 0, 0);

        companyId = castToLongOrUseDefault(typedArray.getString(R.styleable.SignUpScreenlet_companyId),
            LiferayServerContext.getCompanyId());

        anonymousApiUserName = typedArray.getString(R.styleable.SignUpScreenlet_anonymousApiUserName);

        anonymousApiPassword = typedArray.getString(R.styleable.SignUpScreenlet_anonymousApiPassword);

        autoLogin = typedArray.getBoolean(R.styleable.SignUpScreenlet_autoLogin, true);

        int storageValue = typedArray.getInt(R.styleable.SignUpScreenlet_credentialsStorage, StorageType.NONE.toInt());

        credentialsStorage = StorageType.valueOf(storageValue);

        autoLogin = typedArray.getBoolean(R.styleable.SignUpScreenlet_autoLogin, true);

        int authMethodId = typedArray.getInt(R.styleable.SignUpScreenlet_basicAuthMethod, 0);
        basicAuthMethod = BasicAuthMethod.getValue(authMethodId);

        int layoutId = typedArray.getResourceId(R.styleable.SignUpScreenlet_layoutId, getDefaultLayoutId());

        typedArray.recycle();

        return LayoutInflater.from(context).inflate(layoutId, null);
    }

    @Override
    protected SignUpInteractor createInteractor(String actionName) {
        return new SignUpInteractor();
    }

    @Override
    protected void onUserAction(String userActionName, SignUpInteractor interactor, Object... args) {
        SignUpViewModel viewModel = getViewModel();

        String firstName = viewModel.getFirstName();
        String middleName = viewModel.getMiddleName();
        String lastName = viewModel.getLastName();
        String emailAddress = viewModel.getEmailAddress();
        String password = viewModel.getPassword();
        String screenName = viewModel.getScreenName();
        String jobTitle = viewModel.getJobTitle();
        Locale locale = getResources().getConfiguration().locale;

        interactor.start(companyId, firstName, middleName, lastName, emailAddress, screenName, password, jobTitle,
            locale, anonymousApiUserName, anonymousApiPassword);
    }
}