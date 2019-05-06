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

package com.liferay.mobile.screens.auth.login;

import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.User;

public interface LoginListener {

    /**
     * Called when login successfully completes. The `user` parameter contains
     * a set of the logged in user’s attributes. The supported keys are
     * the same as those in the portal’s {@link User} entity.
     */
    void onLoginSuccess(User user);

    /**
     * Called when an error occurs in the process.
     *
     * @param e exception
     */
    void onLoginFailure(Exception e);

    /**
     * Called when the browser is opened to authenticate.
     * {@link AuthenticationType} should be OAUTH2REDIRECT
     */
    void onAuthenticationBrowserShown();
}