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

package com.liferay.mobile.screens.ddl.field;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class StringField extends Field<String> {

	public StringField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);
	}

	@Override
	protected String convertFromString(String stringValue) {
		return stringValue;
	}

	@Override
	protected String convertToString(String value) {
		return value;
	}

	@Override
	protected String convertToLabel(String value) {
		return value;
	}

}
