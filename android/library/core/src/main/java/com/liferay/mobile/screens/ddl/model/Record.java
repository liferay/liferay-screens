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

			public Record createFromParcel(Parcel in, ClassLoader loader) {
				return new Record(in, loader);
			}

			@Override
			public Record createFromParcel(Parcel in) {
				assert false;
				return null;
			}

			public Record[] newArray(int size) {
				return new Record[size];
			}

		};


	public Record(Locale locale) {
		_locale = locale;
	}

	private Record(Parcel in, ClassLoader loader) {
		Parcelable[] array = in.readParcelableArray(loader);
		_fields = new ArrayList(Arrays.asList(array));
		_creatorUserId = in.readLong();
		_structureId = in.readLong();
		_recordSetId = in.readLong();
		_recordId = in.readLong();
		_locale = (Locale) in.readSerializable();
	}

	public void parseXsd(String xsd) throws SAXException {
		XSDParser parser = new XSDParser();

		try {
			_fields = parser.parse(xsd, _locale);
		} catch (SAXException e) {
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

	public long getRecordSetId() {
		return _recordSetId;
	}

	public long getRecordId() {
		return _recordId;
	}

	public long getStructureId() {
		return _structureId;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public Map<String, String> getData() {
		Map<String, String> values = new HashMap<>(_fields.size());

		for (Field f : _fields) {
			String fieldValue = f.toData();

			if (fieldValue != null && !fieldValue.isEmpty()) {
				values.put(f.getName(), fieldValue);
			}
		}

		return values;
	}

	public Locale getLocale() {
		return _locale;
	}

	public void setRecordId(long recordId) {
		_recordId = recordId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public void setStructureId(long structureId) {
		_structureId = structureId;
	}

	public void setCreatorUserId(long value) {
		_creatorUserId = value;
	}

	public void setValues(Map<String,Object> values) {
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

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeParcelableArray(_fields.toArray(new Field[0]), flags);
		destination.writeLong(_creatorUserId);
		destination.writeLong(_structureId);
		destination.writeLong(_recordSetId);
		destination.writeLong(_recordId);
		destination.writeSerializable(_locale);
	}

	private List<Field> _fields = new ArrayList<>();
	private long _creatorUserId;
	private long _structureId;
	private long _recordSetId;
	private long _recordId;
	private Locale _locale;

}