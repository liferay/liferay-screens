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

package com.liferay.mobile.screens.ddl.list;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLEntry implements Parcelable {

	// TODO check if we should merge with DDLRecord when DDLForm branch is integrated

	public static final Creator<DDLEntry> CREATOR =
		new Creator<DDLEntry>() {

			public DDLEntry createFromParcel(Parcel in) {
				return new DDLEntry(in);
			}

			public DDLEntry[] newArray(int size) {
				return new DDLEntry[size];
			}
		};

	public DDLEntry(Map<String, Object> values) {
		_values = values;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(_values);
	}

    public String getValue(String field) {
	    String value = null;
	    Object valueObject = _values.get("modelValues");
	    HashMap valueHashmap = (HashMap) valueObject;
	    Object fieldValueObject = valueHashmap.get(field);
	    String fieldValueObjectString = fieldValueObject.toString();
	    value = fieldValueObjectString;
        //return ((HashMap<String,String>) _values.get("modelValues")).get(field).toString();
	return value;
    }

	public Object getAttributes(String field) {
		return ((HashMap<String,Object>) _values.get("modelAttributes")).get(field);
	}

	private DDLEntry(Parcel in) {
		_values = new HashMap<>();
		in.readMap(_values, ClassLoader.getSystemClassLoader());
	}

	private Map<String, Object> _values;

}
