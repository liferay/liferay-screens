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

package com.liferay.mobile.screens.base.thread.interactors;

import android.support.annotation.NonNull;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.base.thread.IdCache;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadRecordThreadInteractorImpl
	extends BaseCachedThreadRemoteInteractor<DDLFormListener, DDLFormLoadRecordThreadEvent> {

	public DDLFormLoadRecordThreadInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public DDLFormLoadRecordThreadEvent execute(Object[] args) throws Exception {

		Record record = (Record) args[0];

		validate(record);

		Session session = SessionContext.createSessionFromCurrentSession();
		ScreensDDLRecordConnector ddlRecordService = ServiceProvider.getInstance().getScreensDDLRecordConnector(session);

		JSONObject jsonObject = ddlRecordService.getDdlRecord(record.getRecordId(), record.getLocale().toString());
		return new DDLFormLoadRecordThreadEvent(jsonObject, record);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onDDLFormRecordLoadFailed(e);
	}

	@Override
	public void onSuccess(DDLFormLoadRecordThreadEvent event) throws JSONException {

		Map<String, Object> valuesAndAttributes = getStringObjectMap(event.getJSONObject());
		event.getRecord().setValuesAndAttributes(valuesAndAttributes);
		event.getRecord().refresh();

		getListener().onDDLFormRecordLoaded(event.getRecord());
	}

	@Override
	protected IdCache getCachedContent(Object[] args) {
		Record record = (Record) args[0];

		return new IdCacheImpl(String.valueOf(record.getRecordId()));
	}

	@Override
	protected Class<DDLFormLoadRecordThreadEvent> getEventClass() {
		return DDLFormLoadRecordThreadEvent.class;
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