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

package com.liferay.mobile.screens.ddm.form.model;

import java.util.List;

/**
 * @author Paulo Cruz
 */
public class FormInstanceRecord {

    private long formInstanceRecordId;
    private List<FieldValue> fieldValues;

    public long getFormInstanceRecordId() {
        return formInstanceRecordId;
    }

    public void setFormInstanceRecordId(long formInstanceRecordId) {
        this.formInstanceRecordId = formInstanceRecordId;
    }

    public List<FieldValue> getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(List<FieldValue> fieldValues) {
        this.fieldValues = fieldValues;
    }

    public boolean isDraft() {
        return formInstanceRecordId == 0;
    }
}
