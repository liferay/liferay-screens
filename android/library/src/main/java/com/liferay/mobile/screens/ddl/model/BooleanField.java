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
import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class BooleanField extends Field<Boolean> {

    public static final Parcelable.ClassLoaderCreator<BooleanField> CREATOR =
        new Parcelable.ClassLoaderCreator<BooleanField>() {

            @Override
            public BooleanField createFromParcel(Parcel source, ClassLoader loader) {
                return new BooleanField(source, loader);
            }

            public BooleanField createFromParcel(Parcel in) {
                throw new AssertionError("");
            }

            public BooleanField[] newArray(int size) {
                return new BooleanField[size];
            }
        };

    public BooleanField() {
        super();
    }

    public BooleanField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);
    }

    protected BooleanField(Parcel in, ClassLoader classLoader) {
        super(in, classLoader);
    }

    @Override
    protected Boolean convertFromString(String stringValue) {
        if (stringValue == null) {
            return null;
        }
        return Boolean.valueOf(stringValue.toLowerCase());
    }

    @Override
    protected String convertToData(Boolean value) {
        if (value == null) {
            return null;
        }

        return (value) ? "true" : "false";
    }

    @Override
    protected String convertToFormattedString(Boolean value) {
        //TODO localized yes/no
        if (value == null) {
            return null;
        }

        return (value) ? "Yes" : "No";
    }
}
