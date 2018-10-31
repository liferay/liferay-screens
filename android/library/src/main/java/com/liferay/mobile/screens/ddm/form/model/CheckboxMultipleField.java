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

package com.liferay.mobile.screens.ddm.form.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;
import com.liferay.mobile.screens.ddl.model.SelectableOptionsField;
import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class CheckboxMultipleField extends SelectableOptionsField {

    public static final Parcelable.ClassLoaderCreator<SelectableOptionsField> CREATOR =
        new Parcelable.ClassLoaderCreator<SelectableOptionsField>() {

            @Override
            public SelectableOptionsField createFromParcel(Parcel source, ClassLoader loader) {
                return new CheckboxMultipleField(source, loader);
            }

            public SelectableOptionsField createFromParcel(Parcel in) {
                throw new AssertionError();
            }

            public SelectableOptionsField[] newArray(int size) {
                return new CheckboxMultipleField[size];
            }
        };

    private boolean isShowAsSwitcher;

    public CheckboxMultipleField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);

        isShowAsSwitcher = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.SWITCHER_KEY));
    }

    public CheckboxMultipleField(Parcel in, ClassLoader loader) {
        super(in, loader);

        isShowAsSwitcher = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        super.writeToParcel(destination, flags);

        destination.writeInt(isShowAsSwitcher ? 1 : 0);
    }

    public boolean isShowAsSwitcher() {
        return isShowAsSwitcher;
    }
}
