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

import com.liferay.mobile.screens.util.LiferayLogger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * @author Jose Manuel Navarro
 */
public class DateField extends Field<Date> {

	public static final Parcelable.Creator<DateField> CREATOR =
		new Parcelable.Creator<DateField>() {

			public DateField createFromParcel(Parcel in) {
				return new DateField(in);
			}

			public DateField[] newArray(int size) {
				return new DateField[size];
			}
		};


	public DateField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);

		init(locale);
	}

	protected DateField(Parcel in) {
		super(in);

		init(getCurrentLocale());
	}

	@Override
	protected Date convertFromString(String stringValue) {
		if (stringValue == null || stringValue.isEmpty()) {
			return null;
		}

		try {
			return _SERVER_YYYY_FORMAT.parse(stringValue);
		}
		catch (ParseException e) {
			try {
				return _PREDEFINED_MM_DD_FORMAT.parse(stringValue);
			}
			catch (ParseException e1) {
				LiferayLogger.e("Parse error " + stringValue, e);
			}
		}

		return null;
	}

	@Override
	protected String convertToData(Date value) {
		return (value == null) ? null : _SERVER_YYYY_FORMAT.format(value);
	}

	@Override
	protected String convertToFormattedString(Date value) {
		return (value == null) ? null : _clientFormat.format(value);
	}

	private void init(Locale locale) {
		_clientFormat = DateFormat.getDateInstance(DateFormat.LONG, locale);
		_clientFormat.setTimeZone(_GMT_TIMEZONE);
	}

	private static final DateFormat _SERVER_YYYY_FORMAT = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
	private static final DateFormat _PREDEFINED_MM_DD_FORMAT = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

	private static final TimeZone _GMT_TIMEZONE = TimeZone.getTimeZone("GMT");
	private DateFormat _clientFormat;

	static {
		_SERVER_YYYY_FORMAT.setTimeZone(_GMT_TIMEZONE);
		_PREDEFINED_MM_DD_FORMAT.setTimeZone(_GMT_TIMEZONE);
	}

}