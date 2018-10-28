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
 * @author Javier Gamarra
 */
public class ImageField extends StringField implements Parcelable {

    public static final ClassLoaderCreator<ImageField> CREATOR = new ClassLoaderCreator<ImageField>() {

        @Override
        public ImageField createFromParcel(Parcel source, ClassLoader loader) {
            return new ImageField(source, loader);
        }

        public ImageField createFromParcel(Parcel in) {
            throw new AssertionError();
        }

        public ImageField[] newArray(int size) {
            return new ImageField[size];
        }
    };

    public ImageField() {
        super();
    }

    public ImageField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);
    }

    protected ImageField(Parcel in, ClassLoader loader) {
        super(in, loader);
    }
}
