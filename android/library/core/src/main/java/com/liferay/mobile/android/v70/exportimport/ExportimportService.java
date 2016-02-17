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

package com.liferay.mobile.android.v70.exportimport;

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
public class ExportimportService extends BaseService {

	public ExportimportService(Session session) {
		super(session);
	}

	public JSONObject exportPortletInfoAsFile(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/exportimport/export-portlet-info-as-file", _params);
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

	public Long exportLayoutsAsFileInBackground(long exportImportConfigurationId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("exportImportConfigurationId", exportImportConfigurationId);

			_command.put("/exportimport/export-layouts-as-file-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public Long exportLayoutsAsFileInBackground(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/exportimport/export-layouts-as-file-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public Long exportPortletInfoAsFileInBackground(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/exportimport/export-portlet-info-as-file-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public Long importLayoutsInBackground(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/import-layouts-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public void importPortletInfo(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

	public Long importPortletInfoInBackground(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/import-portlet-info-in-background", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getLong(0);
	}

	public JSONObject validateImportLayoutsFile(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/validate-import-layouts-file", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject validateImportPortletInfo(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/validate-import-portlet-info", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.upload(_command);

		if (_result == null) {
			return null;
		}

		return _result.getJSONObject(0);
	}

	public JSONObject exportLayoutsAsFile(JSONObjectWrapper exportImportConfiguration) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);

			_command.put("/exportimport/export-layouts-as-file", _params);
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

	public void importLayouts(JSONObjectWrapper exportImportConfiguration, UploadData file) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			mangleWrapper(_params, "exportImportConfiguration", "com.liferay.portlet.exportimport.model.ExportImportConfiguration", exportImportConfiguration);
			_params.put("file", checkNull(file));

			_command.put("/exportimport/import-layouts", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.upload(_command);
	}

}