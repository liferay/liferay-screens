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
	public static final String MODEL_VALUES = "modelValues";
	public static final String MODEL_ATTRIBUTES = "modelAttributes";

	public Record(Locale locale) {
		_locale = locale;
	}

	public Record(Map<String, Object> valuesAndAttributes) {
		_valuesAndAttributes = valuesAndAttributes;
		parseServerValues();
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
		destination.writeParcelableArray(_fields.toArray(new Field[_fields.size()]), flags);
		destination.writeSerializable(_locale);
		destination.writeValue(_creatorUserId);
		destination.writeValue(_structureId);
		destination.writeValue(_recordSetId);
		destination.writeValue(_recordId);
		destination.writeMap(getValuesAndAttributes());
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

	public Locale getLocale() {
		return _locale;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setValues(Map<String, Object> values) {
		for (Field f : _fields) {
			Object fieldValue = values.get(f.getName());
			if (fieldValue != null) {
				f.setCurrentValue(f.convertFromString(fieldValue.toString()));
			}
		}
	}

	public boolean isRecordStructurePresent() {
		return (_fields.size() > 0);
	}

	/**
	 * renamed from getValue()
	 *
	 * @param field
	 * @return server value of that field
	 */
	public Object getServerValue(String field) {
		return getModelValues() == null ? null : getModelValues().get(field);
	}

	/**
	 * renamed from getAttributes()
	 *
	 * @param field
	 * @return server attribute of that field
	 */
	public Object getServerAttribute(String field) {
		return getModelAttributes() == null ? null : getModelAttributes().get(field);
	}

	public Map<String, Object> getValuesAndAttributes() {
		return _valuesAndAttributes;
	}

	public void setValuesAndAttributes(Map<String, Object> valuesAndAttributes) {
		_valuesAndAttributes = valuesAndAttributes;
		parseServerValues();
	}

	public HashMap<String, Object> getModelValues() {
		return (HashMap<String, Object>) _valuesAndAttributes.get(MODEL_VALUES);
	}

	public HashMap<String, Object> getModelAttributes() {
		return (HashMap<String, Object>) _valuesAndAttributes.get(MODEL_ATTRIBUTES);
	}

	private Record(Parcel in, ClassLoader loader) {
		Parcelable[] array = in.readParcelableArray(getClass().getClassLoader());
		_fields = new ArrayList(Arrays.asList(array));
		_locale = (Locale) in.readSerializable();
		_creatorUserId = (Long) in.readValue(Long.class.getClassLoader());
		_structureId = (Long) in.readValue(Long.class.getClassLoader());
		_recordSetId = (Long) in.readValue(Long.class.getClassLoader());
		_recordId = (Long) in.readValue(Long.class.getClassLoader());

		_valuesAndAttributes = new HashMap<>();
		in.readMap(_valuesAndAttributes, loader);
	}

	private void parseServerValues() {
		//FIXME
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

	private List<Field> _fields = new ArrayList<>();
	private Long _creatorUserId;
	private Long _structureId;
	private Long _recordSetId;
	private Long _recordId;
	private Locale _locale;
	private Map<String, Object> _valuesAndAttributes;
}