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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Jose Manuel Navarro
 */
public class StringWithOptionsField extends Field<ArrayList<StringWithOptionsField.Option>> {

	public static final Parcelable.Creator<StringWithOptionsField> CREATOR =
		new Parcelable.Creator<StringWithOptionsField>() {

			public StringWithOptionsField createFromParcel(Parcel in) {
				return new StringWithOptionsField(in);
			}

			public StringWithOptionsField[] newArray(int size) {
				return new StringWithOptionsField[size];
			}
		};

	public StringWithOptionsField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);

		List<Map<String, String>> availableOptions =
			(List<Map<String, String>>) attributes.get("options");

		if (availableOptions == null) {
			_availableOptions = new ArrayList<>();
		}
		else {
			_availableOptions = new ArrayList<>(availableOptions.size());

			for (Map<String, String> optionMap : availableOptions) {
				_availableOptions.add(new Option(optionMap));
			}
		}

		Object multipleValue = attributes.get("multiple");
		_multiple = (multipleValue != null) ? Boolean.valueOf(multipleValue.toString()) : false;

		ArrayList<Option> predefinedOptions =
			convertFromString(getAttributeStringValue(attributes, "predefinedValue"));

		setPredefinedValue(predefinedOptions);
		setCurrentValue(predefinedOptions);
	}

	protected StringWithOptionsField(Parcel in) {
		super(in);

		_availableOptions = (ArrayList<Option>) in.readSerializable();
		_multiple = (in.readInt() == 1);
	}

	public List<Option> getAvailableOptions() {
		return _availableOptions;
	}

	@Override
	public ArrayList<Option> getCurrentValue() {
		ArrayList<Option> options = super.getCurrentValue();

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
			ArrayList<Option> options = new ArrayList<>();

			options.add(option);

			setCurrentValue(options);
		}
		else {
			ArrayList<Option> options = getCurrentValue();

			if (options == null) {
				options = new ArrayList<>();
			}

			if (!options.contains(option)) {
				options.add(option);
			}
		}
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		super.writeToParcel(destination, flags);

		destination.writeSerializable(_availableOptions);
		destination.writeInt(_multiple ? 1 : 0);
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
	protected ArrayList<Option> convertFromString(String stringValue) {
		if (stringValue == null) {
			return null;
		}
		if (stringValue.isEmpty()) {
			return new ArrayList<>();
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

		ArrayList<Option> result = new ArrayList<>(1);

		if (foundOption != null) {
			result.add(foundOption);
		}

		return result;
	}

	@Override
	protected String convertToData(ArrayList<Option> selectedOptions) {
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
	protected String convertToFormattedString(ArrayList<Option> value) {
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

	private ArrayList<Option> _availableOptions;
	private boolean _multiple;

	public static class Option implements Serializable {

		public String label;
		public String name;
		public String value;

		public Option(Map<String, String> optionMap) {
			this(optionMap.get("label"), optionMap.get("name"), optionMap.get("value"));
		}

		public Option(String label, String name, String value) {
			this.label = label;
			this.name = name;
			this.value = value;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (obj instanceof Option) {
				Option opt = (Option) obj;

				if (name != null) {
					return label.equals(opt.label) && value.equals(opt.value) && name.equals(opt.name);
				}
				else {
					return label.equals(opt.label) && value.equals(opt.value);
				}
			}

			return super.equals(obj);
		}
	}

}