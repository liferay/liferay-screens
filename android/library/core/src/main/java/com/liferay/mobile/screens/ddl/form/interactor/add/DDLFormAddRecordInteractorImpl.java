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

package com.liferay.mobile.screens.ddl.form.interactor.add;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecord.DDLRecordService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormAddRecordInteractorImpl
	extends BaseRemoteInteractor<DDLFormListener> implements DDLFormAddRecordInteractor {

	public DDLFormAddRecordInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void addRecord(long groupId, Record record) throws Exception {
		validate(groupId, record);

		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", record.getCreatorUserId());
		serviceContextAttributes.put("scopeGroupId", groupId);

		JSONObject fieldsValues = new JSONObject(record.getData());

		JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);

		getDDLRecordService(record).addRecord(
			groupId, record.getRecordSetId(), 0, fieldsValues, serviceContextWrapper);
	}

	public void onEvent(DDLFormAddRecordEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormRecordAddFailed(event.getException());
		}
		else {
			try {
				long recordId = event.getJSONObject().getLong("recordId");

				event.getRecord().setRecordId(recordId);

				getListener().onDDLFormRecordAdded(event.getRecord());
			}
			catch (JSONException e) {
				getListener().onDDLFormRecordAddFailed(event.getException());
			}
		}
	}

	protected DDLRecordService getDDLRecordService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new DDLFormAddRecordCallback(getTargetScreenletId(), record));

		return new DDLRecordService(session);
	}

	protected void validate(long groupId, Record record) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId cannot be 0 or negative");
		}

		if (record == null) {
			throw new IllegalArgumentException("record cannot be null");
		}

		if (record.getFieldCount() == 0) {
			throw new IllegalArgumentException("Record's fields cannot be empty");
		}

		if (record.getCreatorUserId() <= 0) {
			throw new IllegalArgumentException("Record's userId cannot be 0 or negative");
		}

		if (record.getRecordSetId() <= 0) {
			throw new IllegalArgumentException("Record's recordSetId cannot be 0 or negative");
		}
	}

}