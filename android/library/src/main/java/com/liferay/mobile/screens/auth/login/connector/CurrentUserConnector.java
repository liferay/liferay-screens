package com.liferay.mobile.screens.auth.login.connector;

import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface CurrentUserConnector {
    JSONObject getCurrentUser() throws Exception;
}
