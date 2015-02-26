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

import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Jose Manuel Navarro
 */
public interface DDLFormListener {

	void onDDLFormLoaded(Record record);

	void onDDLFormRecordLoaded(Record record);

	void onDDLFormRecordAdded(Record record);

	void onDDLFormRecordUpdated(Record record);

	void onDDLFormLoadFailed(Exception e);

	void onDDLFormRecordLoadFailed(Exception e);

	void onDDLFormRecordAddFailed(Exception e);

	void onDDLFormUpdateRecordFailed(Exception e);

	void onDDLFormFileUploaded(DocumentField file);

	void onDDLFormFileUploadFailed(DocumentField file, Exception e);

}