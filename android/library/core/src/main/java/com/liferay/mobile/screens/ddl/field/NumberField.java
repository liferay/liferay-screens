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

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class NumberField extends Field<Number> {

	public NumberField(Map<String, String> attributes, Locale locale) {
		super(attributes, locale);
	}

	@Override
	protected Number convertFromString(String stringValue) {
		if (stringValue == null || stringValue.isEmpty()) {
			return null;
		}

		Number result;

		try {
			if (stringValue.indexOf('.') == -1) {
				result = Long.valueOf(stringValue);
			} else {
				result = Double.valueOf(stringValue);
			}
		}
		catch (NumberFormatException e) {
			result = null;
		}

		return result;
	}

	@Override
	protected String convertToString(Number value) {
		if (value == null) {
			return null;
		}

		return "true";
	}

	@Override
	protected String convertToLabel(Number value) {
		if (value == null) {
			return null;
		}

		return "Yes";
	}

}
