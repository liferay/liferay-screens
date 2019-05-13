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
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Victor Oliveira
 */
public abstract class OptionsField<T extends Serializable> extends Field<T> {

    public ArrayList<Option> getAvailableOptions() {
        return availableOptions;
    }

    public void setAvailableOptions(ArrayList<Option> availableOptions) {
        this.availableOptions = availableOptions;
    }

    private ArrayList<Option> availableOptions;

    OptionsField() {
        super();
    }

    OptionsField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
        super(attributes, locale, defaultLocale);

        List<Map<String, String>> availableOptions =
            (List<Map<String, String>>) FormFieldKeys.getValueFromArrayKey(attributes, FormFieldKeys.OPTIONS_KEY);

        if (availableOptions == null) {
            this.availableOptions = new ArrayList<>();
        } else {
            this.availableOptions = new ArrayList<>(availableOptions.size());

            for (Map<String, String> optionMap : availableOptions) {
                this.availableOptions.add(new Option(optionMap));
            }
        }
    }

    OptionsField(ArrayList<Option> options) {
        availableOptions = options;
    }

    OptionsField(Parcel in, ClassLoader loader) {
        super(in, loader);

        availableOptions = (ArrayList<Option>) in.readSerializable();
    }

    protected Option findOptionByValue(String value) {
        if (availableOptions == null) {
            return null;
        }

        for (Option option : availableOptions) {
            if (option.value.equals(value)) {
                return option;
            }
        }

        return null;
    }

    protected Option findOptionByLabel(String label) {
        if (availableOptions == null) {
            return null;
        }

        for (Option option : availableOptions) {
            if (option.label.equals(label)) {
                return option;
            }
        }

        return null;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        super.writeToParcel(destination, flags);

        destination.writeSerializable(availableOptions);
    }
}
