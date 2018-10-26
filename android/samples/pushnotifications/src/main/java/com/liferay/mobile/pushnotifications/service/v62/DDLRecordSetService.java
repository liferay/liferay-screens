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

package com.liferay.mobile.pushnotifications.service.v62;

import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class DDLRecordSetService extends BaseService {

    public DDLRecordSetService(Session session) {
        super(session);
    }

    public JSONObject addRecordSet(long groupId, long ddmStructureId, String recordSetKey, JSONObject nameMap,
        JSONObject descriptionMap, int minDisplayRows, int scope, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("ddmStructureId", ddmStructureId);
            _params.put("recordSetKey", checkNull(recordSetKey));
            _params.put("nameMap", checkNull(nameMap));
            _params.put("descriptionMap", checkNull(descriptionMap));
            _params.put("minDisplayRows", minDisplayRows);
            _params.put("scope", scope);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/ddlrecordset/add-record-set", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public void deleteRecordSet(long recordSetId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("recordSetId", recordSetId);

            _command.put("/ddlrecordset/delete-record-set", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        session.invoke(_command);
    }

    public JSONObject getRecordSet(long recordSetId) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("recordSetId", recordSetId);

            _command.put("/ddlrecordset/get-record-set", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONArray search(long companyId, long groupId, String keywords, int scope, int start, int end,
        JSONObjectWrapper orderByComparator) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("groupId", groupId);
            _params.put("keywords", checkNull(keywords));
            _params.put("scope", scope);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator",
                orderByComparator);

            _command.put("/ddlrecordset/search", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public JSONArray search(long companyId, long groupId, String name, String description, int scope,
        boolean andOperator, int start, int end, JSONObjectWrapper orderByComparator) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("groupId", groupId);
            _params.put("name", checkNull(name));
            _params.put("description", checkNull(description));
            _params.put("scope", scope);
            _params.put("andOperator", andOperator);
            _params.put("start", start);
            _params.put("end", end);
            mangleWrapper(_params, "orderByComparator", "com.liferay.portal.kernel.util.OrderByComparator",
                orderByComparator);

            _command.put("/ddlrecordset/search", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONArray(0);
    }

    public Integer searchCount(long companyId, long groupId, String keywords, int scope) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("groupId", groupId);
            _params.put("keywords", checkNull(keywords));
            _params.put("scope", scope);

            _command.put("/ddlrecordset/search-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public Integer searchCount(long companyId, long groupId, String name, String description, int scope,
        boolean andOperator) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("companyId", companyId);
            _params.put("groupId", groupId);
            _params.put("name", checkNull(name));
            _params.put("description", checkNull(description));
            _params.put("scope", scope);
            _params.put("andOperator", andOperator);

            _command.put("/ddlrecordset/search-count", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getInt(0);
    }

    public JSONObject updateMinDisplayRows(long recordSetId, int minDisplayRows, JSONObjectWrapper serviceContext)
        throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("recordSetId", recordSetId);
            _params.put("minDisplayRows", minDisplayRows);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/ddlrecordset/update-min-display-rows", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updateRecordSet(long recordSetId, long ddmStructureId, JSONObject nameMap,
        JSONObject descriptionMap, int minDisplayRows, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("recordSetId", recordSetId);
            _params.put("ddmStructureId", ddmStructureId);
            _params.put("nameMap", checkNull(nameMap));
            _params.put("descriptionMap", checkNull(descriptionMap));
            _params.put("minDisplayRows", minDisplayRows);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/ddlrecordset/update-record-set", _params);
        } catch (JSONException _je) {
            throw new Exception(_je);
        }

        JSONArray _result = session.invoke(_command);

        if (_result == null) {
            return null;
        }

        return _result.getJSONObject(0);
    }

    public JSONObject updateRecordSet(long groupId, long ddmStructureId, String recordSetKey, JSONObject nameMap,
        JSONObject descriptionMap, int minDisplayRows, JSONObjectWrapper serviceContext) throws Exception {
        JSONObject _command = new JSONObject();

        try {
            JSONObject _params = new JSONObject();

            _params.put("groupId", groupId);
            _params.put("ddmStructureId", ddmStructureId);
            _params.put("recordSetKey", checkNull(recordSetKey));
            _params.put("nameMap", checkNull(nameMap));
            _params.put("descriptionMap", checkNull(descriptionMap));
            _params.put("minDisplayRows", minDisplayRows);
            mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

            _command.put("/ddlrecordset/update-record-set", _params);
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