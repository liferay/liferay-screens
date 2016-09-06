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

package com.liferay.mobile.screens.asset.list;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Silvio Santos
 */
public class AssetEntry implements Parcelable {

	public static final ClassLoaderCreator<AssetEntry> CREATOR = new ClassLoaderCreator<AssetEntry>() {

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
	protected Map<String, Object> _values;

	public AssetEntry() {
		super();
	}

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

	public String getClassName() {
		return (String) _values.get("className");
	}

	public String getMimeType() {
		return (String) _values.get("mimeType");
	}

	public String getUrl() {
		return "/documents/"
			+ _values.get("groupId")
			+ "/"
			+ getFolder()
			+ "/"
			+ encodeUrlString((String) _values.get("title"))
			+ "/"
			+ _values.get("uuid");
	}

	public Map<String, Object> getObject() {
		return (Map<String, Object>) _values.get("object");
	}

	public String getObjectType() {
		return getObject().keySet().iterator().next();
	}

	private long getFolder() {
		if (_values.get("folderId") != null) {
			return (long) _values.get("folderId");
		}
		return 0;
	}

	private String encodeUrlString(String urlToEncode) {
		try {
			return URLEncoder.encode(urlToEncode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LiferayLogger.e("Error encoding string: " + e.getMessage());
			return "";
		}
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

	public String getEntryId() {
		return String.valueOf(_values.get("entryId"));
	}
}