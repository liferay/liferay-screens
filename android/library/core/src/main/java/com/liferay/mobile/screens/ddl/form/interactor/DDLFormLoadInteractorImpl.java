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

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.ddmstructure.DDMStructureService;
import com.liferay.mobile.screens.base.interactor.BaseInteractor;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.util.SessionContext;

import org.json.JSONException;
import org.xml.sax.SAXException;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadInteractorImpl
	extends BaseInteractor<DDLFormListener> implements DDLFormLoadInteractor {

	public DDLFormLoadInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void load(Record record) throws Exception {
		validate(record);

		getDDMStructureService(record).getStructure(record.getStructureId());
	}

	public void onEvent(DDLFormEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormLoadFailed(event.getException());
		}
		else {
			try {
				String xsd = event.getJSONObject().getString("xsd");
				long userId = event.getJSONObject().getLong("userId");

				Record formRecord = event.getRecord();

				formRecord.parseXsd(xsd);

				if (formRecord.getCreatorUserId() == 0) {
					formRecord.setCreatorUserId(userId);
				}

				getListener().onDDLFormLoaded(formRecord);
			}
			catch (JSONException e) {
				getListener().onDDLFormLoadFailed(event.getException());
			}
			catch (SAXException e) {
				getListener().onDDLFormLoadFailed(event.getException());
			}
		}
	}

	protected DDMStructureService getDDMStructureService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new DDLFormCallback(getTargetScreenletId(), record));

		return new DDMStructureService(session);
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be null");
		}

		if (record.getStructureId() <= 0) {
			throw new IllegalArgumentException("Record's structureId cannot be 0 or negative");
		}

		if (record.getLocale() == null) {
			throw new IllegalArgumentException("Record's Locale cannot be null");
		}
	}

}