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
import com.liferay.mobile.screens.base.interactor.BaseCachedWriteRemoteInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Record;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormAddRecordInteractorImpl
	extends BaseCachedWriteRemoteInteractor<DDLFormListener, DDLFormAddRecordEvent>
	implements DDLFormAddRecordInteractor {

	public DDLFormAddRecordInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void addRecord(final long groupId, final Record record) throws Exception {
		validate(groupId, record);

		final JSONObject fieldsValues = new JSONObject(record.getData());

		storeWithCache(groupId, record, fieldsValues);
	}

	public void onEvent(DDLFormAddRecordEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		JSONObject jsonObject = new JSONObject(event.getRecord().getData());
		onEventWithCache(event, event.getGroupId(), event.getRecord(), jsonObject);
	}

	@Override
	public void online(Object... args) throws Exception {

		long groupId = (long) args[0];
		Record record = (Record) args[1];
		JSONObject fieldsValues = (JSONObject) args[2];

		final JSONObject serviceContextAttributes = new JSONObject();
		serviceContextAttributes.put("userId", record.getCreatorUserId());
		serviceContextAttributes.put("scopeGroupId", groupId);

		JSONObjectWrapper serviceContextWrapper = new JSONObjectWrapper(serviceContextAttributes);
		getDDLRecordService(record, groupId).addRecord(groupId, record.getRecordSetId(), 0, fieldsValues, serviceContextWrapper);
	}

	@Override
	protected void notifySuccess(DDLFormAddRecordEvent event) {
		try {
			if (event.getJSONObject().has("recordId")) {
				long recordId = event.getJSONObject().getLong("recordId");
				event.getRecord().setRecordId(recordId);
			}
			getListener().onDDLFormRecordAdded(event.getRecord());
		}
		catch (JSONException e) {
			notifyError(event);
		}
	}

	@Override
	protected void notifyError(DDLFormAddRecordEvent event) {
		getListener().onDDLFormRecordAddFailed(event.getException());
	}

	@Override
	protected void storeToCache(boolean synced, Object... args) {
		long groupId = (long) args[0];
		Record record = (Record) args[1];
		JSONObject fieldsValues = (JSONObject) args[2];

		DDLRecordCache recordCache = new DDLRecordCache(groupId, record, fieldsValues);
		recordCache.setDirty(!synced);
		CacheSQL.getInstance().set(recordCache);

		onEvent(new DDLFormAddRecordEvent(getTargetScreenletId(), record, groupId, fieldsValues));
	}

	protected DDLRecordService getDDLRecordService(Record record, long groupId) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new DDLFormAddRecordCallback(getTargetScreenletId(), record, groupId));
		return new DDLRecordService(session);
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