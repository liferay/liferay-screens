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

package com.liferay.mobile.android.v70.shoppingcoupon;

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
public class ShoppingCouponService extends BaseService {

	public ShoppingCouponService(Session session) {
		super(session);
	}

	public JSONArray search(long groupId, long companyId, String code, boolean active, String discountType, boolean andOperator, int start, int end) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("companyId", companyId);
			_params.put("code", checkNull(code));
			_params.put("active", active);
			_params.put("discountType", checkNull(discountType));
			_params.put("andOperator", andOperator);
			_params.put("start", start);
			_params.put("end", end);

			_command.put("/shopping.shoppingcoupon/search", _params);
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

	public JSONObject addCoupon(String code, boolean autoCode, String name, String description, int startDateMonth, int startDateDay, int startDateYear, int startDateHour, int startDateMinute, int endDateMonth, int endDateDay, int endDateYear, int endDateHour, int endDateMinute, boolean neverExpire, boolean active, String limitCategories, String limitSkus, double minOrder, double discount, String discountType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("code", checkNull(code));
			_params.put("autoCode", autoCode);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("startDateMonth", startDateMonth);
			_params.put("startDateDay", startDateDay);
			_params.put("startDateYear", startDateYear);
			_params.put("startDateHour", startDateHour);
			_params.put("startDateMinute", startDateMinute);
			_params.put("endDateMonth", endDateMonth);
			_params.put("endDateDay", endDateDay);
			_params.put("endDateYear", endDateYear);
			_params.put("endDateHour", endDateHour);
			_params.put("endDateMinute", endDateMinute);
			_params.put("neverExpire", neverExpire);
			_params.put("active", active);
			_params.put("limitCategories", checkNull(limitCategories));
			_params.put("limitSkus", checkNull(limitSkus));
			_params.put("minOrder", minOrder);
			_params.put("discount", discount);
			_params.put("discountType", checkNull(discountType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingcoupon/add-coupon", _params);
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

	public void deleteCoupon(long groupId, long couponId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("couponId", couponId);

			_command.put("/shopping.shoppingcoupon/delete-coupon", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject getCoupon(long groupId, long couponId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("couponId", couponId);

			_command.put("/shopping.shoppingcoupon/get-coupon", _params);
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

	public JSONObject updateCoupon(long couponId, String name, String description, int startDateMonth, int startDateDay, int startDateYear, int startDateHour, int startDateMinute, int endDateMonth, int endDateDay, int endDateYear, int endDateHour, int endDateMinute, boolean neverExpire, boolean active, String limitCategories, String limitSkus, double minOrder, double discount, String discountType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("couponId", couponId);
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("startDateMonth", startDateMonth);
			_params.put("startDateDay", startDateDay);
			_params.put("startDateYear", startDateYear);
			_params.put("startDateHour", startDateHour);
			_params.put("startDateMinute", startDateMinute);
			_params.put("endDateMonth", endDateMonth);
			_params.put("endDateDay", endDateDay);
			_params.put("endDateYear", endDateYear);
			_params.put("endDateHour", endDateHour);
			_params.put("endDateMinute", endDateMinute);
			_params.put("neverExpire", neverExpire);
			_params.put("active", active);
			_params.put("limitCategories", checkNull(limitCategories));
			_params.put("limitSkus", checkNull(limitSkus));
			_params.put("minOrder", minOrder);
			_params.put("discount", discount);
			_params.put("discountType", checkNull(discountType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingcoupon/update-coupon", _params);
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