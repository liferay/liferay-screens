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

import com.liferay.mobile.screens.ddl.XSDParser;

import org.xml.sax.SAXException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Jose Manuel Navarro
 */
public class Record {

	public Record(String xsd, Locale locale) throws SAXException {
		XSDParser parser = new XSDParser();

		_fields = parser.parse(xsd, locale);

		if (_fields == null) {
			_fields = new ArrayList<>();
		}
	}

	public Field getField(int index) {
		return _fields.get(index);
	}

	public int getFieldCount() {
		return _fields.size();
	}

	private List<Field> _fields;
}
