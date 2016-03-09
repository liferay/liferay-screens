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

package com.liferay.mobile.android.v70.shoppingitem;

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
public class ShoppingItemService extends BaseService {

	public ShoppingItemService(Session session) {
		super(session);
	}

	public JSONObject addItem(long groupId, long categoryId, String sku, String name, String description, String properties, String fieldsQuantities, boolean requiresShipping, int stockQuantity, boolean featured, boolean sale, boolean smallImage, String smallImageURL, UploadData smallFile, boolean mediumImage, String mediumImageURL, UploadData mediumFile, boolean largeImage, String largeImageURL, UploadData largeFile, JSONArray itemFields, JSONArray itemPrices, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("sku", checkNull(sku));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("properties", checkNull(properties));
			_params.put("fieldsQuantities", checkNull(fieldsQuantities));
			_params.put("requiresShipping", requiresShipping);
			_params.put("stockQuantity", stockQuantity);
			_params.put("featured", featured);
			_params.put("sale", sale);
			_params.put("smallImage", smallImage);
			_params.put("smallImageURL", checkNull(smallImageURL));
			_params.put("smallFile", checkNull(smallFile));
			_params.put("mediumImage", mediumImage);
			_params.put("mediumImageURL", checkNull(mediumImageURL));
			_params.put("mediumFile", checkNull(mediumFile));
			_params.put("largeImage", largeImage);
			_params.put("largeImageURL", checkNull(largeImageURL));
			_params.put("largeFile", checkNull(largeFile));
			_params.put("itemFields", checkNull(itemFields));
			_params.put("itemPrices", checkNull(itemPrices));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingitem/add-item", _params);
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

	public JSONObject getItem(long itemId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("itemId", itemId);

			_command.put("/shopping.shoppingitem/get-item", _params);
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

	public JSONArray getItems(long groupId, long categoryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);

			_command.put("/shopping.shoppingitem/get-items", _params);
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

	public JSONArray getItems(long groupId, long categoryId, int start, int end, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("start", start);
			_params.put("end", end);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem>", obc);

			_command.put("/shopping.shoppingitem/get-items", _params);
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

	public Integer getItemsCount(long groupId, long categoryId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);

			_command.put("/shopping.shoppingitem/get-items-count", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getInt(0);
	}

	public void deleteItem(long itemId) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("itemId", itemId);

			_command.put("/shopping.shoppingitem/delete-item", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		session.invoke(_command);
	}

	public Integer getCategoriesItemsCount(long groupId, JSONArray categoryIds) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("groupId", groupId);
			_params.put("categoryIds", checkNull(categoryIds));

			_command.put("/shopping.shoppingitem/get-categories-items-count", _params);
		}
		catch (JSONException _je) {
			throw new Exception(_je);
		}

		JSONArray _result = session.invoke(_command);

		if (_result == null) {
			return null;
		}

		return _result.getInt(0);
	}

	public JSONArray getItemsPrevAndNext(long itemId, JSONObjectWrapper obc) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("itemId", itemId);
			mangleWrapper(_params, "obc", "com.liferay.portal.kernel.util.OrderByComparator<com.liferay.shopping.model.ShoppingItem>", obc);

			_command.put("/shopping.shoppingitem/get-items-prev-and-next", _params);
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

	public JSONObject updateItem(long itemId, long groupId, long categoryId, String sku, String name, String description, String properties, String fieldsQuantities, boolean requiresShipping, int stockQuantity, boolean featured, boolean sale, boolean smallImage, String smallImageURL, UploadData smallFile, boolean mediumImage, String mediumImageURL, UploadData mediumFile, boolean largeImage, String largeImageURL, UploadData largeFile, JSONArray itemFields, JSONArray itemPrices, JSONObjectWrapper serviceContext) throws Exception {
		JSONObject _command = new JSONObject();

		try {
			JSONObject _params = new JSONObject();

			_params.put("itemId", itemId);
			_params.put("groupId", groupId);
			_params.put("categoryId", categoryId);
			_params.put("sku", checkNull(sku));
			_params.put("name", checkNull(name));
			_params.put("description", checkNull(description));
			_params.put("properties", checkNull(properties));
			_params.put("fieldsQuantities", checkNull(fieldsQuantities));
			_params.put("requiresShipping", requiresShipping);
			_params.put("stockQuantity", stockQuantity);
			_params.put("featured", featured);
			_params.put("sale", sale);
			_params.put("smallImage", smallImage);
			_params.put("smallImageURL", checkNull(smallImageURL));
			_params.put("smallFile", checkNull(smallFile));
			_params.put("mediumImage", mediumImage);
			_params.put("mediumImageURL", checkNull(mediumImageURL));
			_params.put("mediumFile", checkNull(mediumFile));
			_params.put("largeImage", largeImage);
			_params.put("largeImageURL", checkNull(largeImageURL));
			_params.put("largeFile", checkNull(largeFile));
			_params.put("itemFields", checkNull(itemFields));
			_params.put("itemPrices", checkNull(itemPrices));
			mangleWrapper(_params, "serviceContext", "com.liferay.portal.service.ServiceContext", serviceContext);

			_command.put("/shopping.shoppingitem/update-item", _params);
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

}