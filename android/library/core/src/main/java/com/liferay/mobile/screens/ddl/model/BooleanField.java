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

import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public class BooleanField extends Field<Boolean> {

	public BooleanField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);
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
	protected String convertToLabel(Boolean value) {
		//TODO localized yes/no
		if (value == null) {
			return null;
		}

		return (value) ? "Yes" : "No";
	}

}
