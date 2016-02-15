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

package com.liferay.mobile.android.v70.address;

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
public class AddressService extends BaseService {

	public AddressService(Session session) {
		super(session);
	}

	public JSONObject getAddress(long addressId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("addressId", addressId);

			_command.put("/address/get-address", _params);
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

	public JSONArray getAddresses(String className, long classPK) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);

			_command.put("/address/get-addresses", _params);
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

	public JSONObject addAddress(String className, long classPK, String street1, String street2, String street3, String city, String zip, long regionId, long countryId, long typeId, boolean mailing, boolean primary, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("className", checkNull(className));
			_params.put("classPK", classPK);
			_params.put("street1", checkNull(street1));
			_params.put("street2", checkNull(street2));
			_params.put("street3", checkNull(street3));
			_params.put("city", checkNull(city));
			_params.put("zip", checkNull(zip));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("typeId", typeId);
			_params.put("mailing", mailing);
			_params.put("primary", primary);
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/address/add-address", _params);
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

	public JSONObject updateAddress(long addressId, String street1, String street2, String street3, String city, String zip, long regionId, long countryId, long typeId, boolean mailing, boolean primary) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("addressId", addressId);
			_params.put("street1", checkNull(street1));
			_params.put("street2", checkNull(street2));
			_params.put("street3", checkNull(street3));
			_params.put("city", checkNull(city));
			_params.put("zip", checkNull(zip));
			_params.put("regionId", regionId);
			_params.put("countryId", countryId);
			_params.put("typeId", typeId);
			_params.put("mailing", mailing);
			_params.put("primary", primary);

			_command.put("/address/update-address", _params);
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

	public void deleteAddress(long addressId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("addressId", addressId);

			_command.put("/address/delete-address", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

}