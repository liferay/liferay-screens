/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.util.JSONUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Jose Manuel Navarro
 */
public class Record extends AssetEntry implements WithDDM, Parcelable {

	public static final Parcelable.ClassLoaderCreator<Record> CREATOR =
		new ClassLoaderCreator<Record>() {

			@Override
			public Record createFromParcel(Parcel parcel, ClassLoader classLoader) {
				return new Record(parcel, classLoader);
			}

			public Record createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public Record[] newArray(int size) {
				return new Record[size];
			}
		};

	public static final String MODEL_VALUES = "modelValues";
	public static final String MODEL_ATTRIBUTES = "modelAttributes";

	public Record(Map<String, Object> valuesAndAttributes, Locale locale) {
		super(valuesAndAttributes);

		parseServerValues();
	}

	public Record(Locale locale) {
		super(new HashMap<String, Object>());
		_ddmStructure = new DDMStructure(getValues(), locale);
	}

	public Record(Map<String, Object> stringObjectMap) {
		super(stringObjectMap);
		_ddmStructure = new DDMStructure(stringObjectMap, Locale.US);
		parseServerValues();
	}

	public void refresh() {
		for (Field f : getDDMStructure().getFields()) {
			Object fieldValue = getServerValue(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		super.writeToParcel(destination, flags);
		destination.writeValue(_creatorUserId);
		destination.writeValue(_structureId);
		destination.writeValue(_recordSetId);
		destination.writeValue(_recordId);
	}

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public long getRecordId() {
		return _recordId;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	public long getStructureId() {
		return _structureId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public void setCreatorUserId(long value) {
		_creatorUserId = value;
	}

	public Map<String, String> getData() {
		Map<String, String> values = new HashMap<>(getFields().size());

		for (Field f : getFields()) {
			String fieldValue = f.toData();

			//FIXME - LPS-49460
			// Server rejects the request if the value is empty string.
			// This way we workaround the problem but a field can't be
			// emptied when you're editing an existing row.
			if (fieldValue != null && !fieldValue.isEmpty()) {
				values.put(f.getName(), fieldValue);
			}
		}

		return values;
	}

	public void setValues(Map<String, Object> values) {
		for (Field f : getFields()) {
			Object fieldValue = values.get(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	public boolean isRecordStructurePresent() {
		return (getFields().size() > 0);
	}

	/**
	 * renamed from getValue()
	 *
	 * @param field key of the field
	 * @return server value of that field
	 */
	public Object getServerValue(String field) {
		return getModelValues() == null ? null : getModelValues().get(field);
	}

	/**
	 * renamed from getAttributes()
	 *
	 * @param field key of the field
	 * @return server attribute of that field
	 */
	public Object getServerAttribute(String field) {
		return getModelAttributes() == null ? null : getModelAttributes().get(field);
	}

	public Map<String, Object> getValuesAndAttributes() {
		return _values;
	}

	public void setValuesAndAttributes(Map<String, Object> valuesAndAttributes) {
		_values = valuesAndAttributes;
		parseServerValues();
	}

	public HashMap<String, Object> getModelValues() {
		return (HashMap<String, Object>) _values.get(MODEL_VALUES);
	}

	public HashMap<String, Object> getModelAttributes() {
		return (HashMap<String, Object>) _values.get(MODEL_ATTRIBUTES);
	}

	@Override
	public DDMStructure getDDMStructure() {
		return _ddmStructure;
	}

	public void setDDMStructure(DDMStructure ddmStructure) {
		_ddmStructure = ddmStructure;
	}

	public List<Field> getFields() {
		return getDDMStructure().getFields();
	}

	public int getFieldCount() {
		return _ddmStructure.getFieldCount();
	}

	public Field getField(int i) {
		return _ddmStructure.getField(i);
	}

	public Field getFieldByName(String name) {
		return _ddmStructure.getFieldByName(name);
	}

	public Locale getLocale() {
		return _ddmStructure.getLocale();
	}

	public void parseXsd(String xsd) {
		_ddmStructure.parseXsd(xsd);
	}

	public void parseJson(String definition) {
		//FIXME
		_ddmStructure.parseJson(definition);
	}

	private Record(Parcel in, ClassLoader loader) {
		super(in, loader);
		_creatorUserId = (Long) in.readValue(Long.class.getClassLoader());
		_structureId = (Long) in.readValue(Long.class.getClassLoader());
		_recordSetId = (Long) in.readValue(Long.class.getClassLoader());
		_recordId = (Long) in.readValue(Long.class.getClassLoader());
	}

	private void parseServerValues() {
		//TODO refactor
		Long recordId = JSONUtil.castToLong(getServerAttribute("recordId"));
		if (recordId != null) {
			_recordId = recordId;
		}
		Long recordSetId = JSONUtil.castToLong(getServerAttribute("recordSetId"));
		if (recordSetId != null) {
			_recordSetId = recordSetId;
		}
		Long userId = JSONUtil.castToLong(getServerAttribute("userId"));
		if (userId != null) {
			_creatorUserId = userId;
		}
	}

	private DDMStructure _ddmStructure;
	private Long _creatorUserId;
	private Long _structureId;
	private Long _recordSetId;
	private Long _recordId;
}