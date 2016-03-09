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

package com.liferay.mobile.android.v70.orglabor;

import com.liferay.mobile.android.http.file.UploadData;
import com.liferay.mobile.android.service.BaseService;
import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Bruno Farache
 */
public class OrgLaborService extends BaseService {

	public OrgLaborService(Session session) {
		super(session);
	}

	public JSONObject addOrgLabor(long organizationId, long typeId, int sunOpen, int sunClose, int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen, int thuClose, int friOpen, int friClose, int satOpen, int satClose) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);
			_params.put("typeId", typeId);
			_params.put("sunOpen", sunOpen);
			_params.put("sunClose", sunClose);
			_params.put("monOpen", monOpen);
			_params.put("monClose", monClose);
			_params.put("tueOpen", tueOpen);
			_params.put("tueClose", tueClose);
			_params.put("wedOpen", wedOpen);
			_params.put("wedClose", wedClose);
			_params.put("thuOpen", thuOpen);
			_params.put("thuClose", thuClose);
			_params.put("friOpen", friOpen);
			_params.put("friClose", friClose);
			_params.put("satOpen", satOpen);
			_params.put("satClose", satClose);

			_command.put("/orglabor/add-org-labor", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject updateOrgLabor(long orgLaborId, long typeId, int sunOpen, int sunClose, int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose, int thuOpen, int thuClose, int friOpen, int friClose, int satOpen, int satClose) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("orgLaborId", orgLaborId);
			_params.put("typeId", typeId);
			_params.put("sunOpen", sunOpen);
			_params.put("sunClose", sunClose);
			_params.put("monOpen", monOpen);
			_params.put("monClose", monClose);
			_params.put("tueOpen", tueOpen);
			_params.put("tueClose", tueClose);
			_params.put("wedOpen", wedOpen);
			_params.put("wedClose", wedClose);
			_params.put("thuOpen", thuOpen);
			_params.put("thuClose", thuClose);
			_params.put("friOpen", friOpen);
			_params.put("friClose", friClose);
			_params.put("satOpen", satOpen);
			_params.put("satClose", satClose);

			_command.put("/orglabor/update-org-labor", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject getOrgLabor(long orgLaborId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("orgLaborId", orgLaborId);

			_command.put("/orglabor/get-org-labor", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONArray getOrgLabors(long organizationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("organizationId", organizationId);

			_command.put("/orglabor/get-org-labors", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONArray(0);
	}

	public void deleteOrgLabor(long orgLaborId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("orgLaborId", orgLaborId);

			_command.put("/orglabor/delete-org-labor", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}