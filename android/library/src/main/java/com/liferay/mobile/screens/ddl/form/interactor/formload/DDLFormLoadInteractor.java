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

import android.support.annotation.NonNull;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.http.Method;
import com.liferay.mobile.android.http.Request;
import com.liferay.mobile.android.http.client.OkHttpClientImpl;
import com.liferay.mobile.android.v7.ddmdataproviderinstance.DdmdataproviderinstanceService;
import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.form.DDLFormScreenlet;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.ddl.form.interactor.DDLFormEvent;
import com.liferay.mobile.screens.ddl.model.Field;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.util.ServiceProvider;
import org.json.JSONArray;
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

		DDLFormEvent event = new DDLFormEvent(record, jsonObject);

		Record formRecord = event.getRecord();

		JSONObject ddmStructure = jsonObject.getJSONObject("ddmStructure");
		formRecord.parseDDMStructure(ddmStructure);
		if (jsonObject.has("ddmFormLayout")) {
			formRecord.parsePages(jsonObject.getJSONObject("ddmFormLayout"));
		}

		if (formRecord.getCreatorUserId() == 0) {
			long userId = ddmStructure.getLong("userId");
			formRecord.setCreatorUserId(userId);
		}

		parseDateProviders(formRecord);

		return event;
	}

	private void parseDateProviders(Record record) {
		try {
			for (Field field : record.getFields()) {
				String ddmDataProviderInstance = field.getDdmDataProviderInstance();
				if (!"".equals(ddmDataProviderInstance) && !"[]".equals(ddmDataProviderInstance)) {

					JSONArray jsonArray = new JSONArray(ddmDataProviderInstance);
					long ddmDataProviderInstanceId = jsonArray.getLong(0);

					SelectableOptionsField optionsField = (SelectableOptionsField) field;

					DdmdataproviderinstanceService ddmdataproviderinstanceService =
						new DdmdataproviderinstanceService(getSession());

					JSONObject jsonDataProvider =
						ddmdataproviderinstanceService.getDataProviderInstance(ddmDataProviderInstanceId);

					String definition = jsonDataProvider.getString("definition");
					JSONObject jsonDefinition = new JSONObject(definition);

					SelectableOptionsField.DataProvider dataProvider = parseDataProvider(jsonDefinition);
					optionsField.setDataProvider(dataProvider);

					String body =
						requestDataFromDataProvider(dataProvider.url, dataProvider.username, dataProvider.password);

					JSONArray values = new JSONArray(body);
					for (int i = 0; i < values.length(); i++) {
						SelectableOptionsField.Option option = parseOption(values.getJSONObject(i), dataProvider);
						optionsField.getAvailableOptions().add(option);
					}
				}
			}
		} catch (Exception e) {
			LiferayLogger.e("Error retrieving a data provider", e);
		}
	}

	@NonNull
	private SelectableOptionsField.DataProvider parseDataProvider(JSONObject jsonDefinition) throws JSONException {
		JSONArray fieldValues = jsonDefinition.getJSONArray("fieldValues");
		String url = findAttributeValue(fieldValues, "url");
		String username = findAttributeValue(fieldValues, "username");
		String password = findAttributeValue(fieldValues, "password");
		String key = findAttributeValue(fieldValues, "key");
		String value = findAttributeValue(fieldValues, "value");
		return new SelectableOptionsField.DataProvider(url, username, password, key, value);
	}

	@NonNull
	private SelectableOptionsField.Option parseOption(JSONObject jsonObject,
		SelectableOptionsField.DataProvider dataProvider) throws JSONException {

		String optionName = jsonObject.getString(dataProvider.name);
		String optionValue = jsonObject.getString(dataProvider.value);

		return new SelectableOptionsField.Option(optionName, optionName, optionValue, jsonObject);
	}

	private String requestDataFromDataProvider(String url, String username, String password) throws Exception {
		BasicAuthentication authentication = new BasicAuthentication(username, password);
		Request request = new Request(authentication, Method.POST, getSession().getHeaders(), url, "",
			getSession().getConnectionTimeout(), null);

		return new OkHttpClientImpl().send(request).getBody();
	}

	private String findAttributeValue(JSONArray array, String name) throws JSONException {
		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			String jsonObjectString = jsonObject.getString("name");
			if (name.equals(jsonObjectString)) {
				return jsonObject.getString("value");
			}
		}
		return "";
	}

	@Override
	public void onSuccess(DDLFormEvent event) {
		getListener().onDDLFormLoaded(event.getRecord());
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
