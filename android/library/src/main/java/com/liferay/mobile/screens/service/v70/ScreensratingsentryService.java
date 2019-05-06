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

package com.liferay.mobile.screens.service.v70;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class ScreensratingsentryService extends BaseService {

    public ScreensratingsentryService(Session session) {
        super(session);
    }

    public JSONObject getRatingsEntries(long classPK, String className, int ratingsLength) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("classPK", classPK);
            _params.put("className", checkNull(className));
            _params.put("ratingsLength", ratingsLength);

            _command.put("/screens.screensratingsentry/get-ratings-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject getRatingsEntries(long assetEntryId, int ratingsLength) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("assetEntryId", assetEntryId);
            _params.put("ratingsLength", ratingsLength);

            _command.put("/screens.screensratingsentry/get-ratings-entries", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updateRatingsEntry(long classPK, String className, double score, int ratingsLength)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("classPK", classPK);
            _params.put("className", checkNull(className));
            _params.put("score", score);
            _params.put("ratingsLength", ratingsLength);

            _command.put("/screens.screensratingsentry/update-ratings-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject deleteRatingsEntry(long classPK, String className, int ratingsLength) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("classPK", classPK);
            _params.put("className", checkNull(className));
            _params.put("ratingsLength", ratingsLength);

            _command.put("/screens.screensratingsentry/delete-ratings-entry", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }
}