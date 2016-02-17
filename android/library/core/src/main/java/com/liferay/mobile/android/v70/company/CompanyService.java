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

package com.liferay.mobile.android.v70.company;

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
public class CompanyService extends BaseService {

	public CompanyService(Session session) {
		super(session);
	}

	public JSONObject getCompanyById(long companyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);

			_command.put("/company/get-company-by-id", _params);
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

	public JSONObject updateCompany(long companyId, String virtualHost, String mx, int maxUsers, boolean active) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("virtualHost", checkNull(virtualHost));
			_params.put("mx", checkNull(mx));
			_params.put("maxUsers", maxUsers);
			_params.put("active", active);

			_command.put("/company/update-company", _params);
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

	public JSONObject updateCompany(long companyId, String virtualHost, String mx, String homeURL, boolean logo, byte[] logoBytes, String name, String legalName, String legalId, String legalType, String sicCode, String tickerSymbol, String industry, String type, String size) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("virtualHost", checkNull(virtualHost));
			_params.put("mx", checkNull(mx));
			_params.put("homeURL", checkNull(homeURL));
			_params.put("logo", logo);
			_params.put("logoBytes", toString(logoBytes));
			_params.put("name", checkNull(name));
			_params.put("legalName", checkNull(legalName));
			_params.put("legalId", checkNull(legalId));
			_params.put("legalType", checkNull(legalType));
			_params.put("sicCode", checkNull(sicCode));
			_params.put("tickerSymbol", checkNull(tickerSymbol));
			_params.put("industry", checkNull(industry));
			_params.put("type", checkNull(type));
			_params.put("size", checkNull(size));

			_command.put("/company/update-company", _params);
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

	public JSONObject updateCompany(long companyId, String virtualHost, String mx, String homeURL, String name, String legalName, String legalId, String legalType, String sicCode, String tickerSymbol, String industry, String type, String size) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("virtualHost", checkNull(virtualHost));
			_params.put("mx", checkNull(mx));
			_params.put("homeURL", checkNull(homeURL));
			_params.put("name", checkNull(name));
			_params.put("legalName", checkNull(legalName));
			_params.put("legalId", checkNull(legalId));
			_params.put("legalType", checkNull(legalType));
			_params.put("sicCode", checkNull(sicCode));
			_params.put("tickerSymbol", checkNull(tickerSymbol));
			_params.put("industry", checkNull(industry));
			_params.put("type", checkNull(type));
			_params.put("size", checkNull(size));

			_command.put("/company/update-company", _params);
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

	public void deleteLogo(long companyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);

			_command.put("/company/delete-logo", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject getCompanyByLogoId(long logoId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("logoId", logoId);

			_command.put("/company/get-company-by-logo-id", _params);
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

	public JSONObject getCompanyByMx(String mx) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("mx", checkNull(mx));

			_command.put("/company/get-company-by-mx", _params);
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

	public JSONObject getCompanyByVirtualHost(String virtualHost) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("virtualHost", checkNull(virtualHost));

			_command.put("/company/get-company-by-virtual-host", _params);
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

	public JSONObject getCompanyByWebId(String webId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("webId", checkNull(webId));

			_command.put("/company/get-company-by-web-id", _params);
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

	public void updateDisplay(long companyId, String languageId, String timeZoneId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("languageId", checkNull(languageId));
			_params.put("timeZoneId", checkNull(timeZoneId));

			_command.put("/company/update-display", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateLogo(long companyId, byte[] bytes) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("companyId", companyId);
			_params.put("bytes", toString(bytes));

			_command.put("/company/update-logo", _params);
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

}