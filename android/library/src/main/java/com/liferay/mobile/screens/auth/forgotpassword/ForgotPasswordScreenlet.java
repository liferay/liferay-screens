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

package com.liferay.mobile.screens.auth.forgotpassword;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.forgotpassword.interactor.ForgotPasswordInteractor;
import com.liferay.mobile.screens.auth.forgotpassword.view.ForgotPasswordViewModel;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;

/**
 * @author Silvio Santos
 */
public class ForgotPasswordScreenlet extends BaseScreenlet<ForgotPasswordViewModel, ForgotPasswordInteractor>
    implements ForgotPasswordListener {

    private String anonymousApiPassword;
    private String anonymousApiUserName;
    private long companyId;
    private BasicAuthMethod basicAuthMethod;
    private ForgotPasswordListener listener;

    public ForgotPasswordScreenlet(Context context) {
        super(context);
    }

    public ForgotPasswordScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForgotPasswordScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ForgotPasswordScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onForgotPasswordRequestFailure(Exception e) {
        getViewModel().showFailedOperation(null, e);

        if (listener != null) {
            listener.onForgotPasswordRequestFailure(e);
        }
    }

    @Override
    public void onForgotPasswordRequestSuccess(boolean passwordSent) {
        getViewModel().showFinishOperation(passwordSent);

        if (listener != null) {
            listener.onForgotPasswordRequestSuccess(passwordSent);
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

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long value) {
        companyId = value;
    }

    public BasicAuthMethod getBasicAuthMethod() {
        return basicAuthMethod;
    }

    public void setBasicAuthMethod(BasicAuthMethod basicAuthMethod) {
        this.basicAuthMethod = basicAuthMethod;
        getViewModel().setBasicAuthMethod(basicAuthMethod);
    }

    public ForgotPasswordListener getListener() {
        return listener;
    }

    public void setListener(ForgotPasswordListener listener) {
        this.listener = listener;
    }

    @Override
    protected View createScreenletView(Context context, AttributeSet attributes) {
        TypedArray typedArray =
            context.getTheme().obtainStyledAttributes(attributes, R.styleable.ForgotPasswordScreenlet, 0, 0);

        companyId = castToLongOrUseDefault(typedArray.getString(R.styleable.ForgotPasswordScreenlet_companyId),
            LiferayServerContext.getCompanyId());

        anonymousApiUserName = typedArray.getString(R.styleable.ForgotPasswordScreenlet_anonymousApiUserName);

        anonymousApiPassword = typedArray.getString(R.styleable.ForgotPasswordScreenlet_anonymousApiPassword);

        int layoutId = typedArray.getResourceId(R.styleable.ForgotPasswordScreenlet_layoutId, getDefaultLayoutId());

        View view = LayoutInflater.from(context).inflate(layoutId, null);

        int authMethod = typedArray.getInt(R.styleable.ForgotPasswordScreenlet_basicAuthMethod, 0);
        basicAuthMethod = BasicAuthMethod.getValue(authMethod);
        ((ForgotPasswordViewModel) view).setBasicAuthMethod(basicAuthMethod);

        typedArray.recycle();

        return view;
    }

    @Override
    protected ForgotPasswordInteractor createInteractor(String actionName) {
        return new ForgotPasswordInteractor();
    }

    @Override
    protected void onUserAction(String userActionName, ForgotPasswordInteractor interactor, Object... args) {

        ForgotPasswordViewModel viewModel = getViewModel();

        String login = viewModel.getLogin();
        BasicAuthMethod method = viewModel.getBasicAuthMethod();

        try {
            interactor.start(companyId, login, method, anonymousApiUserName, anonymousApiPassword);
        } catch (Exception e) {
            onForgotPasswordRequestFailure(e);
        }
    }
}