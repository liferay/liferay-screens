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

package com.liferay.mobile.screens.auth.login.view;

import com.liferay.mobile.screens.auth.BasicAuthViewModel;
import com.liferay.mobile.screens.context.AuthenticationType;
import com.liferay.mobile.screens.context.User;

/**
 * @author Silvio Santos
 */
public interface LoginViewModel extends BasicAuthViewModel {

    /**
     * Gets the login information: email, screen name or user ID.
     *
     * @return login
     */
    String getLogin();

    /**
     * Gets the user password.
     *
     * @return password.
     */
    String getPassword();

    /**
     * Sets the {@link AuthenticationType}.
     */
    void setAuthenticationType(AuthenticationType authenticationType);

    /**
     * Called when the login is successfully completed.
     */
    void showFinishOperation(User user);
}