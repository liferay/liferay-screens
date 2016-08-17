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

package com.liferay.mobile.screens.ddl.form;

import com.liferay.mobile.screens.base.thread.listener.OfflineListenerNew;
import com.liferay.mobile.screens.ddl.form.interactor.add.DDLFormAddRecordInteractorImpl;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public interface DDLFormListener extends OfflineListenerNew {

	void onDDLFormLoaded(Record record);

	void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes);

	void onDDLFormRecordAdded(Record record);


	void onDDLFormRecordUpdated(Record record);

	void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject);

	void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e);
}