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
public class StringField extends Field<String> {

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

		if (getText() != null) {
			setReadOnly(true);
		}
	}

	protected StringField(Parcel source, ClassLoader loader) {
		super(source, loader);
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		String currentValue = getCurrentValue();

		if (currentValue != null && isRequired()) {
			String trimmedString = currentValue.trim();

			valid = !trimmedString.isEmpty();
		}

		return valid;
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

}