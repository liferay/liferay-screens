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

package com.liferay.mobile.screens.auth.login.interactor;

import android.text.TextUtils;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.auth.BasicAuthMethod;
import com.liferay.mobile.screens.auth.login.connector.UserConnector;
import com.liferay.mobile.screens.base.interactor.event.BasicEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONObject;

public class LoginBasicInteractor extends BaseLoginInteractor {

    @Override
    public BasicEvent execute(Object... args) throws Exception {

        String login = (String) args[0];
        String password = (String) args[1];

        BasicAuthMethod basicAuthMethod = getBasicAuthMethod(args);

        validate(login, password, basicAuthMethod);

        UserConnector userConnector = getUserConnector(login, password);

        JSONObject jsonObject = getUser(login, basicAuthMethod, userConnector);

        return new BasicEvent(jsonObject);
    }

    private BasicAuthMethod getBasicAuthMethod(Object[] args) {
        return args.length < 2 ? BasicAuthMethod.EMAIL : (BasicAuthMethod) args[2];
    }

    protected JSONObject getUser(String login, BasicAuthMethod basicAuthMethod, UserConnector userConnector)
        throws Exception {
        switch (basicAuthMethod) {
            case USER_ID:
                return userConnector.getUserById(Long.parseLong(login));
            case SCREEN_NAME:
                return userConnector.getUserByScreenName(LiferayServerContext.getCompanyId(), login);
            case EMAIL:
            default:
                return userConnector.getUserByEmailAddress(LiferayServerContext.getCompanyId(), login);
        }
    }

    protected void validate(String login, String password, BasicAuthMethod basicAuthMethod) {
        if (login == null) {
            throw new IllegalArgumentException("Login cannot be empty");
        } else if (password == null) {
            throw new IllegalArgumentException("Password cannot be empty");
        } else if (basicAuthMethod == null) {
            throw new IllegalArgumentException("BasicAuthMethod cannot be empty");
        } else if (basicAuthMethod == BasicAuthMethod.USER_ID && !TextUtils.isDigitsOnly(login)) {
            throw new IllegalArgumentException("UserId has to be a number");
        }
    }

    public UserConnector getUserConnector(String login, String password) {
        Session session = SessionContext.createBasicSession(login, password);
        return ServiceProvider.getInstance().getUserConnector(session);
    }
}