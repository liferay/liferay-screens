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

package com.liferay.mobile.screens.testapp.fullview;

import android.content.Context;
import android.util.AttributeSet;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.auth.login.interactor.BaseLoginInteractor;
import com.liferay.mobile.screens.util.LiferayLogger;

/**
 * @author Javier Gamarra
 */
public class LoginFullScreenlet extends LoginScreenlet {

    public LoginFullScreenlet(Context context) {
        super(context);
    }

    public LoginFullScreenlet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginFullScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public LoginFullScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onUserAction(String userActionName, BaseLoginInteractor interactor, Object... args) {
        LiferayLogger.e("logging call to interactor: " + userActionName);
        super.onUserAction(userActionName, interactor, args);
    }
}
