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

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.ddl.XSDParser;
import com.liferay.mobile.screens.util.JSONUtil;

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Jose Manuel Navarro
 */
public class Record implements Parcelable {

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

	public Record(Locale locale) {
		_locale = locale;
	}

	public Record(Map<String, Object> valuesAndAttributes) {
		_valuesAndAttributes = valuesAndAttributes;
		parseServerValues();
	}

	public Map<String, String> getData() {
		Map<String, String> values = new HashMap<>(_fields.size());

		for (Field f : _fields) {
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

	public void refresh() {
		for (Field f : _fields) {
			Object fieldValue = getServerValue(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	public void parseXsd(String xsd) {
		XSDParser parser = new XSDParser();

		try {
			_fields = parser.parse(xsd, _locale);
		}
		catch (SAXException e) {
			_fields = new ArrayList<>();
		}
	}

	public Field getField(int index) {
		return _fields.get(index);
	}

	public Field getFieldByName(String fieldName) {
		if (fieldName == null) {
			return null;
		}

		for (Field f : _fields) {
			if (fieldName.equals(f.getName())) {
				return f;
			}
		}

		return null;
	}

	public int getFieldCount() {
		return _fields.size();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(_valuesAndAttributes);
		destination.writeParcelableArray(_fields.toArray(new Field[_fields.size()]), flags);
		destination.writeSerializable(_locale);
		writeLong(destination, _creatorUserId);
		writeLong(destination, _structureId);
		writeLong(destination, _recordSetId);
		writeLong(destination, _recordId);
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

	public Locale getLocale() {
		return _locale;
	}

	public boolean isRecordStructurePresent() {
		return (_fields.size() > 0);
	}

	/**
	 * renamed from getValue()
	 * @param field
	 * @return server value of that field
	 */
	public String getServerValue(String field) {
		return getModelValues().get(field);
	}

	/**
	 * renamed from getAttributes()
	 * @param field
	 * @return server attribute of that field
	 */
	public Object getServerAttribute(String field) {
		return getModelAttributes().get(field);
	}

	private Record(Parcel in, ClassLoader loader) {
		Parcelable[] array = in.readParcelableArray(loader);
		_fields = new ArrayList(Arrays.asList(array));
		_creatorUserId = in.readLong();
		_structureId = in.readLong();
		_recordSetId = in.readLong();
		_recordId = in.readLong();
		_locale = (Locale) in.readSerializable();
		_valuesAndAttributes = new HashMap<>();
		in.readMap(_valuesAndAttributes, loader);
	}

	private Map<String, Object> getValuesAndAttributes() {
		return _valuesAndAttributes;
	}

	public void setValuesAndAttributes(Map<String, Object> valuesAndAttributes) {
		_valuesAndAttributes = valuesAndAttributes;
		parseServerValues();
	}

	private void parseServerValues() {
		_recordId = JSONUtil.castToLong(getServerAttribute("recordId"));
		_recordSetId = JSONUtil.castToLong(getServerAttribute("recordSetId"));
		_creatorUserId = JSONUtil.castToLong(getServerAttribute("creatorUserId"));
		_structureId = JSONUtil.castToLong(getServerAttribute("structureId"));
	}

	private void writeLong(Parcel destination, Long field) {
		if (field != null) {
			destination.writeLong(field);
		}
	}

	private HashMap<String, String> getModelValues() {
		return (HashMap<String, String>) _valuesAndAttributes.get("modelValues");
	}

	private HashMap<String, Object> getModelAttributes() {
		return (HashMap<String, Object>) _valuesAndAttributes.get("modelAttributes");
	}

	private List<Field> _fields = new ArrayList<>();
	private Long _creatorUserId;
	private Long _structureId;
	private Long _recordSetId;
	private Long _recordId;
	private Locale _locale;
	private Map<String, Object> _valuesAndAttributes;
}