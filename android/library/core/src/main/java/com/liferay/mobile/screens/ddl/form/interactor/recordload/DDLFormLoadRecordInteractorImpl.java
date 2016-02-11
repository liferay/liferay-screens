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

package com.liferay.mobile.screens.ddl.form.interactor.recordload;

import android.support.annotation.NonNull;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.ddl.form.DDLRecordCache;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.operation.DDLRecordOperation;
import com.liferay.mobile.screens.ddl.form.operation.ScreensDDLRecordOperation;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.util.ServiceVersionFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadRecordInteractorImpl
	extends BaseCachedRemoteInteractor<DDLFormListener, DDLFormLoadRecordEvent>
	implements DDLFormLoadRecordInteractor {

	public DDLFormLoadRecordInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void loadRecord(final Record record) throws Exception {

		validate(record);

		processWithCache(record);
	}

	public void onEvent(DDLFormLoadRecordEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event);

		if (!event.isFailed()) {
			try {
				Map<String, Object> valuesAndAttributes = getStringObjectMap(event.getJSONObject());
				event.getRecord().setValuesAndAttributes(valuesAndAttributes);

				event.getRecord().refresh();

				getListener().onDDLFormRecordLoaded(event.getRecord());
			}
			catch (JSONException e) {
				getListener().onDDLFormRecordLoadFailed(e);
			}
		}
	}

	@Override
	protected void online(Object[] args) throws Exception {

		Record record = (Record) args[0];

		getDDLRecordService(record).getDdlRecord(
			record.getRecordId(), record.getLocale().toString());
	}

	@Override
	protected void notifyError(DDLFormLoadRecordEvent event) {
		getListener().onDDLFormRecordLoadFailed(event.getException());
	}

	@Override
	protected boolean cached(Object[] args) throws Exception {

		Record record = (Record) args[0];

		Cache cache = CacheSQL.getInstance();
		DDLRecordCache recordCache = (DDLRecordCache) cache.getById(
			DefaultCachedType.DDL_RECORD, String.valueOf(record.getRecordId()));

		if (recordCache != null) {
			JSONObject jsonContent = recordCache.getJSONContent();
			onEvent(new DDLFormLoadRecordEvent(getTargetScreenletId(), record, jsonContent.getJSONObject("modelValues")));
			return true;
		}
		return false;
	}

	@Override
	protected void storeToCache(DDLFormLoadRecordEvent event, Object... args) {

		try {
			long groupId = LiferayServerContext.getGroupId();
			JSONObject valuesAndAttributes = new JSONObject();
			valuesAndAttributes.put("modelValues", event.getJSONObject());
			CacheSQL.getInstance().set(new DDLRecordCache(groupId, event.getRecord(), valuesAndAttributes));
		}
		catch (JSONException e) {
			LiferayLogger.e("Couldn't parse JSON values", e);
		}
	}

	protected ScreensDDLRecordOperation getDDLRecordService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new DDLFormLoadRecordCallback(getTargetScreenletId(), record));

		return ServiceVersionFactory.getScreensDDLRecordOperation(session);
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be empty");
		}

		if (record.getRecordId() <= 0) {
			throw new IllegalArgumentException("Record's recordId cannot be 0 or negative");
		}
	}

	@NonNull
	private Map<String, Object> getStringObjectMap(JSONObject jsonObject) throws JSONException {
		Map<String, Object> modelValues = JSONUtil.toMap(jsonObject);
		if (modelValues.containsKey("modelValues")) {
			return modelValues;
		}
		else {
			Map<String, Object> valuesAndAttributes = new HashMap<>();
			valuesAndAttributes.put("modelValues", modelValues);
			return valuesAndAttributes;
		}
	}

}