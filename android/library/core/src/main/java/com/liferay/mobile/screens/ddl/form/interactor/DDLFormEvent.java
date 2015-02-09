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

import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class DDLFormEvent extends JSONObjectEvent {

	public DDLFormEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public DDLFormEvent(int targetScreenletId, JSONObject jsonObject, Locale locale) {
		super(targetScreenletId, jsonObject);

		_locale = locale;
	}

	public DDLFormEvent(int targetScreenletId, JSONObject jsonObject, Record record) {
		super(targetScreenletId, jsonObject);

		_record = record;
	}

	public Locale getLocale() {
		return _locale;
	}

	public Record getRecord() {
		return _record;
	}

	private Locale _locale;
	private Record _record;

}