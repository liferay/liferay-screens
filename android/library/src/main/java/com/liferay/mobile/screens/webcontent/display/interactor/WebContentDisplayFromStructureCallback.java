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

package com.liferay.mobile.screens.webcontent.display.interactor;

import com.liferay.mobile.screens.base.list.interactor.GenericBatchAsyncTaskCallback;
import com.liferay.mobile.screens.ddl.model.WebContent;
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayFromStructureCallback
	extends GenericBatchAsyncTaskCallback<WebContent> {

	public WebContentDisplayFromStructureCallback(int targetScreenletId, Long structureId, String articleId, Locale locale) {
		_targetScreenletId = targetScreenletId;
		_structureId = structureId;
		_articleId = articleId;
		_locale = locale;
	}

	@Override
	public WebContent transform(Object obj) throws Exception {
		JSONArray array = (JSONArray) obj;
		JSONObject values = array.getJSONObject(0);
		WebContent result = new WebContent(JSONUtil.toMap(values), _locale);
		result.parseDDMStructure(array.getJSONObject(1));
		return result;
	}

	@Override
	public void onSuccess(WebContent webContent) {
		EventBusUtil.post(new WebContentDisplayEvent(_targetScreenletId, _structureId, _articleId, _locale, webContent));
	}

	@Override
	public void onSuccess(ArrayList<WebContent> results) {
	}

	@Override
	public void onFailure(Exception exception) {
		EventBusUtil.post(new WebContentDisplayEvent(_targetScreenletId, _structureId, _articleId, _locale, exception));
	}

	private final Long _structureId;
	private final String _articleId;
	private final Locale _locale;

	private int _targetScreenletId;
}