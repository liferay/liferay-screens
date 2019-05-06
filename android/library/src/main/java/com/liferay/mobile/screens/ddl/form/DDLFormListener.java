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

import com.liferay.mobile.screens.base.interactor.listener.BaseCacheListener;
import com.liferay.mobile.screens.ddl.model.DocumentField;
import com.liferay.mobile.screens.ddl.model.Record;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Jose Manuel Navarro
 */
public interface DDLFormListener extends BaseCacheListener {

    /**
     * Called when the form definition successfully loads.
     */
    void onDDLFormLoaded(Record record);

    /**
     * Called when the form record data successfully loads.
     */
    void onDDLFormRecordLoaded(Record record, Map<String, Object> valuesAndAttributes);

    /**
     * Called when the form record is successfully added.
     */
    void onDDLFormRecordAdded(Record record);

    /**
     * Called when the form record data successfully updates.
     */
    void onDDLFormRecordUpdated(Record record);

    /**
     * Called when a specified document field’s upload completes.
     */
    void onDDLFormDocumentUploaded(DocumentField documentField, JSONObject jsonObject);

    /**
     * Called when a specified document field’s upload fails.
     */
    void onDDLFormDocumentUploadFailed(DocumentField documentField, Exception e);
}