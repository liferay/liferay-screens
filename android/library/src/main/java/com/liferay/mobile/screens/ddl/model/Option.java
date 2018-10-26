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
import java.io.Serializable;
import java.util.Map;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class Option implements Serializable, Parcelable {

    public String label;
    public String name;
    public String value;
    public JSONObject data;

    public Option() {
        super();
    }

    public Option(Map<String, String> optionMap) {
        this(optionMap.get("label"), optionMap.get("name"), optionMap.get("value"));
    }

    public Option(String label, String name, String value) {
        this(label, name, value, null);
    }

    public Option(String label, String name, String value, JSONObject data) {
        this.label = label;
        this.name = name;
        this.value = value;
        this.data = data;
    }

    @Override
    public int hashCode() {
        int result = label != null ? label.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Option) {
            Option opt = (Option) obj;

            if (name != null) {
                return label.equals(opt.label) && value.equals(opt.value) && name.equals(opt.name);
            } else {
                return label.equals(opt.label) && value.equals(opt.value);
            }
        }

        return super.equals(obj);
    }

    public static final Parcelable.ClassLoaderCreator<Option> CREATOR = new Parcelable.ClassLoaderCreator<Option>() {

        @Override
        public Option createFromParcel(Parcel source, ClassLoader loader) {
            return new Option(source, loader);
        }

        public Option createFromParcel(Parcel in) {
            throw new AssertionError();
        }

        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

    protected Option(Parcel in, ClassLoader loader) {
        label = in.readString();
        name = in.readString();
        value = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeString(name);
        dest.writeString(value);
    }
}
