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

package com.liferay.mobile.screens.asset;

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
    protected Map<String, Object> values;

    public AssetEntry() {
        super();
    }

    protected AssetEntry(Parcel in, ClassLoader loader) {
        values = new HashMap<>();

        in.readMap(values, loader);
    }

    protected AssetEntry(Map<String, Object> values) {
        // You should use AssetFactory.createInstance to create the right entity
        this.values = values;
    }

    public String getTitle() {
        return (String) values.get("title");
    }

    public String getClassName() {
        return (String) values.get("className");
    }

    public String getMimeType() {
        return (String) values.get("mimeType");
    }

    public String getUrl() {
        return "/documents/" + values.get("groupId") + "/" + getFolder() + "/" + encodeUrlString(
            (String) values.get("title")) + "/" + values.get("uuid");
    }

    public Map<String, Object> getObject() {
        return (Map<String, Object>) values.get("object");
    }

    public String getObjectType() {
        return getObject().keySet().iterator().next();
    }

    private long getFolder() {
        if (values.containsKey("folderId")) {
            return (long) values.get("folderId");
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
        destination.writeMap(values);
    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public long getEntryId() {
        Object entryId = values.get("entryId");
        return entryId instanceof String ? Long.valueOf((String) entryId) : (Integer) entryId;
    }

    public long getClassPK() {
        return Long.parseLong((String) values.get("classPK"));
    }
}