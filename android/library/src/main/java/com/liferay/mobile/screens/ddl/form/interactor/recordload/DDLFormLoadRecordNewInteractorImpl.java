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
import com.liferay.mobile.screens.base.thread.BaseCachedThreadRemoteInteractor;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.connector.ScreensDDLRecordConnector;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadRecordNewInteractorImpl
	extends BaseCachedThreadRemoteInteractor<DDLFormListener, DDLFormEvent> {

	@Override
	public DDLFormEvent execute(Object[] args) throws Exception {

		Record record = (Record) args[0];

		validate(record);

		ScreensDDLRecordConnector ddlRecordService =
			ServiceProvider.getInstance().getScreensDDLRecordConnector(getSession());

		JSONObject jsonObject = ddlRecordService.getDdlRecord(record.getRecordId(), record.getLocale().toString());
		return new DDLFormEvent(record, jsonObject);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, DDLFormScreenlet.LOAD_RECORD_ACTION);
	}

	@Override
	public void onSuccess(DDLFormEvent event) throws Exception {

		Map<String, Object> valuesAndAttributes = getStringObjectMap(event.getJSONObject());

		getListener().onDDLFormRecordLoaded(event.getRecord(), valuesAndAttributes);
	}

	@Override
	protected String getIdFromArgs(Object[] args) {
		Record record = (Record) args[0];
		return String.valueOf(record.getRecordId());
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be empty");
		} else if (record.getRecordId() <= 0) {
			throw new IllegalArgumentException("Record's recordId cannot be 0 or negative");
		}
	}

	@NonNull
	private Map<String, Object> getStringObjectMap(JSONObject jsonObject) throws JSONException {
		Map<String, Object> modelValues = JSONUtil.toMap(jsonObject);
		if (modelValues.containsKey("modelValues")) {
			return modelValues;
		} else {
			Map<String, Object> valuesAndAttributes = new HashMap<>();
			valuesAndAttributes.put("modelValues", modelValues);
			return valuesAndAttributes;
		}
	}
}