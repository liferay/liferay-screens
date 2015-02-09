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
	public void load(long structureId, Locale locale) throws Exception {
		validate(structureId, locale);

		getDDMStructureService(locale).getStructure(structureId);
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

				Record formRecord = new Record(xsd, event.getLocale());

				formRecord.setCreatorUserId(userId);

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

	protected DDMStructureService getDDMStructureService(Locale locale) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new DDLFormCallback(getTargetScreenletId(), locale));

		return new DDMStructureService(session);
	}

	protected void validate(long structureId, Locale locale) {
		if (structureId <= 0) {
			throw new IllegalArgumentException("StructureId cannot be 0 or negative");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be null");
		}
	}

}