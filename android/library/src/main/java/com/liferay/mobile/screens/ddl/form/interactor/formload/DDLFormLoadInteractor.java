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

package com.liferay.mobile.screens.ddl.form.interactor.formload;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadInteractor extends BaseCacheReadInteractor<DDLFormListener, DDLFormEvent> {

	@Override
	public DDLFormEvent execute(Object... args) throws Exception {

		Record record = (Record) args[0];

		validate(record);

		DDMStructureConnector ddmStructureConnector =
			ServiceProvider.getInstance().getDDMStructureConnector(getSession());
		JSONObject jsonObject = ddmStructureConnector.getStructure(record.getStructureId());

		return new DDLFormEvent(record, jsonObject);
	}

	@Override
	public void onSuccess(DDLFormEvent event) {
		Record formRecord = event.getRecord();

		try {
			JSONObject jsonObject = event.getJSONObject();
			JSONObject ddmStructure = jsonObject.getJSONObject("ddmStructure");
			formRecord.parseDDMStructure(ddmStructure);
			if (jsonObject.has("ddmFormLayout")) {
				formRecord.parsePages(jsonObject.getJSONObject("ddmFormLayout"));
			}

			if (formRecord.getCreatorUserId() == 0) {
				long userId = ddmStructure.getLong("userId");
				formRecord.setCreatorUserId(userId);
			}
		} catch (JSONException e) {
			event.setException(e);
			onFailure(event);
			return;
		}

		getListener().onDDLFormLoaded(formRecord);
	}

	@Override
	public void onFailure(DDLFormEvent event) {
		getListener().error(event.getException(), DDLFormScreenlet.LOAD_FORM_ACTION);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		Record record = (Record) args[0];
		return String.valueOf(record.getStructureId());
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be empty");
		} else if (record.getStructureId() <= 0) {
			throw new IllegalArgumentException("Record's structureId cannot be 0 or negative");
		} else if (record.getLocale() == null) {
			throw new IllegalArgumentException("Record's Locale cannot be empty");
		}
	}
}