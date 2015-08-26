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

package com.liferay.mobile.screens.ddl.form.interactor.update;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormBaseCallback;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormUpdateRecordCallback extends DDLFormBaseCallback {

	public DDLFormUpdateRecordCallback(int targetScreenletId, Record record, long groupId) {
		super(targetScreenletId, record);

		_groupId = groupId;
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new DDLFormUpdateRecordEvent(targetScreenletId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, JSONObject result) {
		return new DDLFormUpdateRecordEvent(targetScreenletId, result, getRecord(), _groupId);
	}

	private final long _groupId;
}