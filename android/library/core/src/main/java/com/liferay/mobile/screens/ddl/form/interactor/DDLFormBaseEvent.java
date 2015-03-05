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

/**
 * @author Silvio Santos
 */
public class DDLFormBaseEvent extends JSONObjectEvent {

	public DDLFormBaseEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId, e);
	}

	public DDLFormBaseEvent(int targetScreenletId, JSONObject jsonObject, Record record) {
		super(targetScreenletId, jsonObject);

		_record = record;
	}

	public Record getRecord() {
		return _record;
	}

	private Record _record;

}