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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetEntry implements Parcelable {

	public AssetEntry() {

	}

	public static final ClassLoaderCreator<AssetEntry> CREATOR =
		new ClassLoaderCreator<AssetEntry>() {

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

	protected AssetEntry(Parcel in, ClassLoader loader) {
		_values = new HashMap<>();

		in.readMap(_values, loader);
	}

	public AssetEntry(Map<String, Object> values) {
		_values = values;
	}

	public String getTitle() {
		return (String) _values.get("title");
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(_values);
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	protected Map<String, Object> _values;
}