/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.thread.interactors;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.connector.DDLRecordConnector;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormAddRecordThreadInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<DDLFormListener, DDLFormAddRecordThreadEvent> {

	public DDLFormAddRecordThreadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public DDLFormAddRecordThreadEvent createEvent(Object[] args) throws Exception {

		long groupId = (long) args[0];
		Record record = (Record) args[1];

		validate(groupId, record);

		final JSONObject fieldsValues = new JSONObject(record.getData());

		final JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", record.getCreatorUserId());
		serviceContextAttributes.put("scopeGroupId", groupId);

		JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);

		return new DDLFormAddRecordThreadEvent(record, groupId, fieldsValues, serviceContextWrapper);
	}

	@Override
	public DDLFormAddRecordThreadEvent execute(DDLFormAddRecordThreadEvent event) throws Exception {
		Session session = SessionContext.createSessionFromCurrentSession();
		DDLRecordConnector ddlRecordConnector = ServiceProvider.getInstance().getDDLRecordConnector(session);
		JSONObject jsonObject = ddlRecordConnector.addRecord(event.getGroupId(), event.getRecord().getRecordSetId(), 0, event.getJSONObject(), event.getServiceContextWrapper());
		return new DDLFormAddRecordThreadEvent(jsonObject);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onDDLFormRecordAddFailed(e);
	}

	@Override
	public void onSuccess(DDLFormAddRecordThreadEvent event) throws JSONException {
		if (event.getJSONObject().has("recordId")) {
			long recordId = event.getJSONObject().getLong("recordId");
			event.getRecord().setRecordId(recordId);
		}
		getListener().onDDLFormRecordAdded(event.getRecord());
	}

	@Override
	protected Class getEventClass() {
		return DDLFormAddRecordThreadEvent.class;
	}

	protected void validate(long groupId, Record record) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId cannot be 0 or negative");
		}

		if (record == null) {
			throw new IllegalArgumentException("record cannot be empty");
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