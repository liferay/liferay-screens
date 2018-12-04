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

import com.liferay.mobile.screens.ddl.StringValidator;
import com.liferay.mobile.screens.util.FieldValidationState;
import com.liferay.mobile.screens.util.ValidationUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class StringField extends OptionsField<String> {

    private StringValidator stringValidator;

    public static final Parcelable.ClassLoaderCreator<StringField> CREATOR =
        new Parcelable.ClassLoaderCreator<StringField>() {

            @Override
            public StringField createFromParcel(Parcel source, ClassLoader loader) {
                return new StringField(source, loader);
            }

            public StringField createFromParcel(Parcel in) {
                throw new AssertionError();
            }

            public StringField[] newArray(int size) {
                return new StringField[size];
            }
        };

    public StringField() {
        super();
    }

    public StringField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);

        initializeValidation(attributes);

        if (getText() != null) {
            setReadOnly(true);
        }

    }

    protected StringField(Parcel source, ClassLoader loader) {
        super(source, loader);
    }

    @Override
    protected boolean doValidate() {
        String currentValue = getCurrentValue();

        if (currentValue != null && !currentValue.isEmpty()) {
            return checkLocalValidation(currentValue);
        }

        return checkIsValid();
    }

    @Override
    protected String convertFromString(String stringValue) {
        return stringValue;
    }

    @Override
    protected String convertToData(String value) {
        return value;
    }

    @Override
    protected String convertToFormattedString(String value) {
        return value;
    }

    public StringValidator getStringValidator() {
        return stringValidator;
    }

    private boolean checkIsValid() {
        if (isRequired()) {
            setFieldValidationState(FieldValidationState.REQUIRED_WITHOUT_VALUE);
        } else {
            setFieldValidationState(FieldValidationState.VALID);
        }

        return !isRequired();
    }

    private boolean checkLocalValidation(String currentValue) {
        currentValue = currentValue.trim();
        boolean isValid = stringValidator.validate(currentValue);

        if (!isValid) {
            setFieldValidationState(FieldValidationState.INVALID_BY_LOCAL_RULE);
        }

        return isValid;
    }

    private void initializeValidation(Map<String, Object> attributes) {
        Map<String, String> validation = ValidationUtil.getValidationFromAttributes(attributes);
        stringValidator = StringValidator.parseStringValidation(validation);
        doValidate();
    }

}