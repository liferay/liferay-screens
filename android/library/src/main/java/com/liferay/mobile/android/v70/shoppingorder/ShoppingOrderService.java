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

package com.liferay.mobile.android.v70.shoppingorder;

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
public class ShoppingOrderService extends BaseService {

	public ShoppingOrderService(Session session) {
		super(session);
	}

	public JSONObject getOrder(long groupId, long orderId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("orderId", orderId);

			_command.put("/shopping.shoppingorder/get-order", _params);
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

	public void sendEmail(long groupId, long orderId, String emailType, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("orderId", orderId);
			_params.put("emailType", checkNull(emailType));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingorder/send-email", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void completeOrder(long groupId, String number, String ppTxnId, String ppPaymentStatus, double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("number", checkNull(number));
			_params.put("ppTxnId", checkNull(ppTxnId));
			_params.put("ppPaymentStatus", checkNull(ppPaymentStatus));
			_params.put("ppPaymentGross", ppPaymentGross);
			_params.put("ppReceiverEmail", checkNull(ppReceiverEmail));
			_params.put("ppPayerEmail", checkNull(ppPayerEmail));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingorder/complete-order", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public void deleteOrder(long groupId, long orderId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("orderId", orderId);

			_command.put("/shopping.shoppingorder/delete-order", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public JSONObject updateOrder(long groupId, long orderId, String ppTxnId, String ppPaymentStatus, double ppPaymentGross, String ppReceiverEmail, String ppPayerEmail) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("orderId", orderId);
			_params.put("ppTxnId", checkNull(ppTxnId));
			_params.put("ppPaymentStatus", checkNull(ppPaymentStatus));
			_params.put("ppPaymentGross", ppPaymentGross);
			_params.put("ppReceiverEmail", checkNull(ppReceiverEmail));
			_params.put("ppPayerEmail", checkNull(ppPayerEmail));

			_command.put("/shopping.shoppingorder/update-order", _params);
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

	public JSONObject updateOrder(long groupId, long orderId, String billingFirstName, String billingLastName, String billingEmailAddress, String billingCompany, String billingStreet, String billingCity, String billingState, String billingZip, String billingCountry, String billingPhone, boolean shipToBilling, String shippingFirstName, String shippingLastName, String shippingEmailAddress, String shippingCompany, String shippingStreet, String shippingCity, String shippingState, String shippingZip, String shippingCountry, String shippingPhone, String ccName, String ccType, String ccNumber, int ccExpMonth, int ccExpYear, String ccVerNumber, String comments) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("orderId", orderId);
			_params.put("billingFirstName", checkNull(billingFirstName));
			_params.put("billingLastName", checkNull(billingLastName));
			_params.put("billingEmailAddress", checkNull(billingEmailAddress));
			_params.put("billingCompany", checkNull(billingCompany));
			_params.put("billingStreet", checkNull(billingStreet));
			_params.put("billingCity", checkNull(billingCity));
			_params.put("billingState", checkNull(billingState));
			_params.put("billingZip", checkNull(billingZip));
			_params.put("billingCountry", checkNull(billingCountry));
			_params.put("billingPhone", checkNull(billingPhone));
			_params.put("shipToBilling", shipToBilling);
			_params.put("shippingFirstName", checkNull(shippingFirstName));
			_params.put("shippingLastName", checkNull(shippingLastName));
			_params.put("shippingEmailAddress", checkNull(shippingEmailAddress));
			_params.put("shippingCompany", checkNull(shippingCompany));
			_params.put("shippingStreet", checkNull(shippingStreet));
			_params.put("shippingCity", checkNull(shippingCity));
			_params.put("shippingState", checkNull(shippingState));
			_params.put("shippingZip", checkNull(shippingZip));
			_params.put("shippingCountry", checkNull(shippingCountry));
			_params.put("shippingPhone", checkNull(shippingPhone));
			_params.put("ccName", checkNull(ccName));
			_params.put("ccType", checkNull(ccType));
			_params.put("ccNumber", checkNull(ccNumber));
			_params.put("ccExpMonth", ccExpMonth);
			_params.put("ccExpYear", ccExpYear);
			_params.put("ccVerNumber", checkNull(ccVerNumber));
			_params.put("comments", checkNull(comments));

			_command.put("/shopping.shoppingorder/update-order", _params);
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