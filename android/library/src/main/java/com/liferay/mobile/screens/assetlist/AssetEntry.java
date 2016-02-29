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

package com.liferay.mobile.screens.assetlist;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.ddl.model.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetEntry implements Parcelable {

	public static final Parcelable.ClassLoaderCreator<AssetEntry> CREATOR =
		new Parcelable.ClassLoaderCreator<AssetEntry>() {

			@Override
			public AssetEntry createFromParcel(Parcel source, ClassLoader loader) {
				return new AssetEntry(source, loader);
			}

			public AssetEntry createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public AssetEntry[] newArray(int size) {
				return new AssetEntry[size];
			}
		};

	public AssetEntry(Map<String, Object> values) {
		_values = values;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getTitle() {
		return (String) _values.get("title");
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(_values);
		destination.writeParcelableArray(_fields.toArray(new Field[_fields.size()]), flags);
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	public List<Field> getFields() {
		return _fields;
	}

	//FIXME !!!!

	public void setFields(List<Field> fields) {
		_fields = fields;
	}

	public Field getFieldByName(String name) {
		for (Field field : _fields) {
			if (field.getName().equals(name)) {
				return field;
			}
		}
		return null;
	}

	private AssetEntry(Parcel in, ClassLoader loader) {
		_values = new HashMap<>();

		in.readMap(_values, ClassLoader.getSystemClassLoader());

		Parcelable[] array = in.readParcelableArray(getClass().getClassLoader());
		_fields = new ArrayList(Arrays.asList(array));
	}

	private Map<String, Object> _values;

	private List<Field> _fields;
}