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

package com.liferay.mobile.screens.service.v62;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class ScreensuserService extends BaseService {

    public ScreensuserService(Session session) {
        super(session);
    }

    public JSONObject getCurrentUser() throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _command.put("/screens-web.screensuser/get-current-user", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public Boolean sendPasswordByEmailAddress(long companyId, String emailAddress) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("emailAddress", checkNull(emailAddress));

            _command.put("/screens-web.screensuser/send-password-by-email-address", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean sendPasswordByScreenName(long companyId, String screenName) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("screenName", checkNull(screenName));

            _command.put("/screens-web.screensuser/send-password-by-screen-name", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }

    public Boolean sendPasswordByUserId(long userId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("userId", userId);

            _command.put("/screens-web.screensuser/send-password-by-user-id", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getBoolean(0);
    }
}