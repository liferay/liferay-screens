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
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.DDLFormListener;
import com.liferay.mobile.screens.ddl.model.Record;
import com.liferay.mobile.screens.service.v62.MobilewidgetsddlrecordService;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONException;

/**
 * @author Jose Manuel Navarro
 */
public class DDLFormLoadRecordInteractorImpl
	extends BaseRemoteInteractor<DDLFormListener> implements DDLFormLoadRecordInteractor {

	public DDLFormLoadRecordInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void loadRecord(Record record) throws Exception {
		validate(record);

		getDDLRecordService(record).getDdlRecord(
			record.getRecordId(), record.getLocale().toString());
	}

	public void onEvent(DDLFormEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDDLFormRecordLoadFailed(event.getException());
		}
		else {
			try {
				event.getRecord().setValues(JSONUtil.toMap(event.getJSONObject()));

				getListener().onDDLFormRecordLoaded(event.getRecord());
			}
			catch (JSONException e) {
				getListener().onDDLFormRecordLoadFailed(event.getException());
			}
		}
	}

	protected MobilewidgetsddlrecordService getDDLRecordService(Record record) {
		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new DDLFormCallback(getTargetScreenletId(), record));

		return new MobilewidgetsddlrecordService(session);
	}

	protected void validate(Record record) {
		if (record == null) {
			throw new IllegalArgumentException("record cannot be null");
		}

		if (record.getRecordId() <= 0) {
			throw new IllegalArgumentException("Record's recordId cannot be 0 or negative");
		}
	}

}