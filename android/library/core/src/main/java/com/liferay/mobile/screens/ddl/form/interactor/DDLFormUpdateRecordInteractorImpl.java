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

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddlrecord.DDLRecordService;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.SessionContext;

import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormUpdateRecordInteractorImpl
	extends BaseInteractor<DDLFormListener> implements DDLFormUpdateRecordInteractor {

	public DDLFormUpdateRecordInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void updateRecord(long groupId, Record record) throws Exception {
		validate(groupId, record);

		JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", record.getCreatorUserId());
		serviceContextAttributes.put("scopeGroupId", groupId);

		JSONObject fieldsValues = new JSONObject(record.getData());

		JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);

		getDDLRecordService(record).updateRecord(
			record.getRecordId(), 0, fieldsValues, true, serviceContextWrapper);
	}

	public void onEvent(DDLFormEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormUpdateRecordFailed(event.getException());
		}
		else {
			getListener().onDDLFormRecordUpdated(event.getRecord());
		}
	}

	protected DDLRecordService getDDLRecordService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new DDLFormCallback(getTargetScreenletId(), record));

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

		if (record.getRecordId() <= 0) {
			throw new IllegalArgumentException("Record's recordId cannot be 0 or negative");
		}
	}

}