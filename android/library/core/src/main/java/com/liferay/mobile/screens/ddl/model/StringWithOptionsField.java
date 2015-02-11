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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Jose Manuel Navarro
 */
public class StringWithOptionsField extends Field<List<StringWithOptionsField.Option>> {

	public static class Option {

		public Option(Map<String,String> optionMap) {
			this(optionMap.get("label"), optionMap.get("name"), optionMap.get("value"));
		}

		public Option(String label, String name, String value) {
			this.label = label;
			this.name = name;
			this.value = value;
		}

		protected String label;
		protected String name;
		protected String value;
	}

	public StringWithOptionsField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);

		List<Map<String,String>> availableOptions = (List<Map<String,String>>) attributes.get("options");

		if (availableOptions == null) {
			_availableOptions = Collections.emptyList();
		}
		else {
			_availableOptions = new ArrayList<Option>(availableOptions.size());

			for (Map<String,String> optionMap : availableOptions) {
				_availableOptions.add(new Option(optionMap));
			}
		}

		Object multipleValue = attributes.get("multiple");
		_multiple = (multipleValue != null) ? Boolean.valueOf(multipleValue.toString()) : false;
	}

	public List<Option> getAvailableOptions() {
		return _availableOptions;
	}

	@Override
	public List<Option> getCurrentValue() {
		List<Option> options = super.getCurrentValue();

		if (options == null) {
			options = new ArrayList<>();
		}

		return options;
	}

	public void clearOption(Option option) {
		List<Option> options = getCurrentValue();

		if (options == null) {
			return;
		}

		options.remove(option);
	}

	public void selectOption(Option option) {
		if (!isMultiple()) {
			List<Option> options = new ArrayList<>();
			options.add(option);

			setCurrentValue(options);
		}
		else {
			List<Option> options = getCurrentValue();

			if (options == null) {
				options = new ArrayList<>();
			}

			if (!options.contains(option)) {
				options.add(option);
			}
		}
	}

	public boolean isMultiple() {
		// Multiple selection is not supported yet
		return false;
	}

	@Override
	protected boolean doValidate() {
		List<Option> options = getCurrentValue();

		return (options != null && !options.isEmpty());
	}

	@Override
	protected List<Option> convertFromString(String stringValue) {
		if (stringValue == null) {
			return null;
		}
		if (stringValue.isEmpty()) {
			return new ArrayList<Option>();
		}

		if (stringValue.startsWith("[")) {
			stringValue = stringValue.substring(1, stringValue.length() - 1);
		}

		//TODO only works with one option
		String[] values = stringValue.split(",");
		String fistOptionString = values[0];

		if (fistOptionString.startsWith("\"")) {
			fistOptionString = fistOptionString.substring(1, fistOptionString.length() - 1);
		}

		Option foundOption = findOptionByLabel(fistOptionString);
		if (foundOption == null) {
			foundOption = findOptionByValue(fistOptionString);
		}

		List<Option> result = new ArrayList<Option>(1);

		if (foundOption != null) {
			result.add(foundOption);
		}

		return result;
	}

	@Override
	protected String convertToData(List<Option> selectedOptions) {
		if (selectedOptions == null || selectedOptions.isEmpty()) {
			return "[]";
		}

		StringBuilder result = new StringBuilder();
		boolean isFirst = true;

		result.append('[');

		for (Option op : selectedOptions) {
			if (isFirst) {
				result.append('"');
				isFirst = false;
			}
			else {
				result.append(", \"");
			}

			result.append(op.value);

			result.append('"');
		}

		result.append(']');

		return result.toString();
	}

	@Override
	protected String convertToFormattedString(List<Option> value) {
		if (value == null || value.isEmpty()) {
			return "";
		}

		return value.get(0).label;
	}

	protected Option findOptionByValue(String value) {
		if (_availableOptions == null) {
			return null;
		}

		for (Option o : _availableOptions) {
			if (o.value.equals(value)) {
				return o;
			}
		}

		return null;
	}

	protected Option findOptionByLabel(String label) {
		if (_availableOptions == null) {
			return null;
		}

		for (Option o : _availableOptions) {
			if (o.label.equals(label)) {
				return o;
			}
		}

		return null;
	}

	private List<Option> _availableOptions;
	private boolean _multiple;

}
