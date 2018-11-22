/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.ddl.NumberValidator;
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class NumberField extends Field<Number> {

    public static final Parcelable.ClassLoaderCreator<NumberField> CREATOR =
        new Parcelable.ClassLoaderCreator<NumberField>() {

            @Override
            public NumberField createFromParcel(Parcel source, ClassLoader loader) {
                return new NumberField(source, loader);
            }

            public NumberField createFromParcel(Parcel in) {
                throw new AssertionError();
            }

            public NumberField[] newArray(int size) {
                return new NumberField[size];
            }
        };
    private NumberFormat labelFormatter;
    private NumberFormat defaultLabelFormatter;
    private NumberValidator numberValidator;

    public NumberField() {
        super();
    }

    public NumberField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);

        Object validationObj = attributes.get(FormFieldKeys.VALIDATION_KEY);

        Map<String, String> validation;
        if (validationObj != null && validationObj instanceof Map) {
            validation = (Map<String, String>) validationObj;
        } else {
            validation = new HashMap<>();
        }
        numberValidator = NumberValidator.parseNumberValidator(validation, getName());
    }

    protected NumberField(Parcel in, ClassLoader loader) {
        super(in, loader);
    }

    @Override
    protected Number convertFromString(String stringValue) {
        if (stringValue == null || stringValue.isEmpty()) {
            return null;
        }

        ParsePosition pos = new ParsePosition(0);
        Number value = getLabelFormatter().parse(stringValue, pos);

        if (stringValue.length() == pos.getIndex()) {
            return value;
        } else {
            pos = new ParsePosition(0);
            value = defaultLabelFormatter.parse(stringValue, pos);
            return stringValue.length() == pos.getIndex() ? value : null;
        }
    }

    @Override
    protected boolean doValidate() {
        Number currentValue = getCurrentValue();

        if (currentValue != null) {
            return numberValidator.validate(currentValue);
        }

        return !isRequired();
    }

    @Override
    protected String convertToData(Number value) {
        return (value == null) ? null : value.toString();
    }

    @Override
    protected String convertToFormattedString(Number value) {
        return (value == null) ? "" : getLabelFormatter().format(value);
    }

    private NumberFormat getLabelFormatter() {
        if (labelFormatter == null || defaultLabelFormatter == null) {
            labelFormatter = NumberFormat.getNumberInstance(getCurrentLocale());
            defaultLabelFormatter = NumberFormat.getNumberInstance(getDefaultLocale());
        }
        return labelFormatter;
    }
}