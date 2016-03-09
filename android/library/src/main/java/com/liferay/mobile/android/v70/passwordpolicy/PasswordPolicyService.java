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

package com.liferay.mobile.android.v70.passwordpolicy;

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
public class PasswordPolicyService extends BaseService {

	public PasswordPolicyService(Session session) {
		super(session);
	}

	public JSONObject addPasswordPolicy(String name, String description, boolean changeable, boolean changeRequired, long minAge, boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric, int minLength, int minLowerCase, int minNumbers, int minSymbols, int minUpperCase, String regex, boolean history, int historyCount, boolean expireable, long maxAge, long warningTime, int graceLimit, boolean lockout, int maxFailure, long lockoutDuration, long resetFailureCount, long resetTicketMaxAge, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("changeable", changeable);
			_params.put("changeRequired", changeRequired);
			_params.put("minAge", minAge);
			_params.put("checkSyntax", checkSyntax);
			_params.put("allowDictionaryWords", allowDictionaryWords);
			_params.put("minAlphanumeric", minAlphanumeric);
			_params.put("minLength", minLength);
			_params.put("minLowerCase", minLowerCase);
			_params.put("minNumbers", minNumbers);
			_params.put("minSymbols", minSymbols);
			_params.put("minUpperCase", minUpperCase);
			_params.put("regex", checkNull(regex));
			_params.put("history", history);
			_params.put("historyCount", historyCount);
			_params.put("expireable", expireable);
			_params.put("maxAge", maxAge);
			_params.put("warningTime", warningTime);
			_params.put("graceLimit", graceLimit);
			_params.put("lockout", lockout);
			_params.put("maxFailure", maxFailure);
			_params.put("lockoutDuration", lockoutDuration);
			_params.put("resetFailureCount", resetFailureCount);
			_params.put("resetTicketMaxAge", resetTicketMaxAge);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/passwordpolicy/add-password-policy", _params);
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

	public JSONObject updatePasswordPolicy(long passwordPolicyId, String name, String description, boolean changeable, boolean changeRequired, long minAge, boolean checkSyntax, boolean allowDictionaryWords, int minAlphanumeric, int minLength, int minLowerCase, int minNumbers, int minSymbols, int minUpperCase, String regex, boolean history, int historyCount, boolean expireable, long maxAge, long warningTime, int graceLimit, boolean lockout, int maxFailure, long lockoutDuration, long resetFailureCount, long resetTicketMaxAge, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("changeable", changeable);
			_params.put("changeRequired", changeRequired);
			_params.put("minAge", minAge);
			_params.put("checkSyntax", checkSyntax);
			_params.put("allowDictionaryWords", allowDictionaryWords);
			_params.put("minAlphanumeric", minAlphanumeric);
			_params.put("minLength", minLength);
			_params.put("minLowerCase", minLowerCase);
			_params.put("minNumbers", minNumbers);
			_params.put("minSymbols", minSymbols);
			_params.put("minUpperCase", minUpperCase);
			_params.put("regex", checkNull(regex));
			_params.put("history", history);
			_params.put("historyCount", historyCount);
			_params.put("expireable", expireable);
			_params.put("maxAge", maxAge);
			_params.put("warningTime", warningTime);
			_params.put("graceLimit", graceLimit);
			_params.put("lockout", lockout);
			_params.put("maxFailure", maxFailure);
			_params.put("lockoutDuration", lockoutDuration);
			_params.put("resetFailureCount", resetFailureCount);
			_params.put("resetTicketMaxAge", resetTicketMaxAge);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/passwordpolicy/update-password-policy", _params);
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

	public void deletePasswordPolicy(long passwordPolicyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);

			_command.put("/passwordpolicy/delete-password-policy", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject fetchPasswordPolicy(long passwordPolicyId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("passwordPolicyId", passwordPolicyId);

			_command.put("/passwordpolicy/fetch-password-policy", _params);
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