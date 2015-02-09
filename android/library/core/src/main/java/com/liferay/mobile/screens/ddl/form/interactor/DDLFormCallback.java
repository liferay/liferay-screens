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

package com.liferay.mobile.screens.ddl.form.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormCallback extends InteractorAsyncTaskCallback<JSONObject> {

	public DDLFormCallback(int targetScreenletId, Locale locale) {
		super(targetScreenletId);

		_locale = locale;
	}

	public DDLFormCallback(int targetScreenletId, Record record) {
		super(targetScreenletId);

		_record = record;
	}

	@Override
	public JSONObject transform(Object obj) throws Exception {
		return (JSONObject)obj;
	}

	public Locale getLocale() {
		return _locale;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new DDLFormEvent(targetScreenletId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		DDLFormEvent resultEvent = null;

		if (_locale != null) {
			resultEvent = new DDLFormEvent(targetScreenletId, result, _locale);
		}
		else if (_record != null) {
			resultEvent = new DDLFormEvent(targetScreenletId, result, _record);
		}

		return resultEvent;
	}

	private Locale _locale;
	private Record _record;

}