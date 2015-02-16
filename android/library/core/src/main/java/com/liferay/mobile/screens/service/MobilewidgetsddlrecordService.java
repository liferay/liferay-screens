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

package com.liferay.mobile.screens.service;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class MobilewidgetsddlrecordService extends BaseService {

    public MobilewidgetsddlrecordService(Session session) {
        super(session);
    }

    public void getDDLEntries(JSONObject _params, String locale) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            _params.put("locale", locale);

            _command.put("/mobile-widgets-web/mobilewidgetsddlrecord/get-ddl-records", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public void getEntriesCount(JSONObject _params) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            _command.put("/mobile-widgets-web/mobilewidgetsddlrecord/get-ddl-records-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }
}