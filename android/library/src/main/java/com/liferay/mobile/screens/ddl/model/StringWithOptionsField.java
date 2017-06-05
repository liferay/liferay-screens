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

	public static final Parcelable.ClassLoaderCreator<StringWithOptionsField> CREATOR =
		new Parcelable.ClassLoaderCreator<StringWithOptionsField>() {

			@Override
			public StringWithOptionsField createFromParcel(Parcel source, ClassLoader loader) {
				return new StringWithOptionsField(source, loader);
			}

			public StringWithOptionsField createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public StringWithOptionsField[] newArray(int size) {
				return new StringWithOptionsField[size];
			}
		};
	private ArrayList<Option> availableOptions;
	private boolean multiple;

	public StringWithOptionsField() {
		super();
	}

	public StringWithOptionsField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);

		List<Map<String, String>> availableOptions = (List<Map<String, String>>) attributes.get("options");

		if (availableOptions == null) {
			this.availableOptions = new ArrayList<>();
		} else {
			this.availableOptions = new ArrayList<>(availableOptions.size());

			for (Map<String, String> optionMap : availableOptions) {
				this.availableOptions.add(new Option(optionMap));
			}
		}

		Object multipleValue = attributes.get("multiple");
		multiple = (multipleValue != null) ? Boolean.valueOf(multipleValue.toString()) : false;
		if ("checkbox_multiple".equals(attributes.get("type"))) {
			multiple = true;
		}

		ArrayList<Option> predefinedOptions = convertFromString(getAttributeStringValue(attributes, "predefinedValue"));

		setPredefinedValue(predefinedOptions);
		setCurrentValue(predefinedOptions);
	}

	protected StringWithOptionsField(Parcel in, ClassLoader loader) {
		super(in, loader);

		availableOptions = (ArrayList<Option>) in.readSerializable();
		multiple = in.readInt() == 1;
	}

	public List<Option> getAvailableOptions() {
		return availableOptions;
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

		if (options.isEmpty()) {
			return;
		}

		options.remove(option);
	}

	public boolean isSelected(Option availableOption) {
		List<Option> options = getCurrentValue();
		return options.contains(availableOption);
	}

	public void selectOption(Option option) {
		if (!isMultiple()) {
			ArrayList<Option> options = new ArrayList<>();

			options.add(option);

			setCurrentValue(options);
		} else {
			ArrayList<Option> options = getCurrentValue();

			if (!options.contains(option)) {
				options.add(option);
			}
		}
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		super.writeToParcel(destination, flags);

		destination.writeSerializable(availableOptions);
		destination.writeInt(multiple ? 1 : 0);
	}

	public boolean isMultiple() {
		// Multiple selection is supported on select fields
		return multiple;
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

		ArrayList<Option> results = new ArrayList<>();

		String[] values = stringValue.split(",");
		for (String value : values) {

			if (value.startsWith("\"")) {
				value = value.substring(1, value.length() - 1);
			}

			Option foundOption = findOptionByLabel(value);
			if (foundOption == null) {
				foundOption = findOptionByValue(value);
			}

			if (foundOption != null) {
				results.add(foundOption);
			}
		}
		return results;
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
			} else {
				result.append(", \"");
			}

			result.append(op.value);

			result.append('"');
		}

		result.append(']');

		return result.toString();
	}

	@Override
	protected String convertToFormattedString(ArrayList<Option> values) {
		if (values == null || values.isEmpty()) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder(values.get(0).label);

		for (int i = 1; i < values.size(); i++) {
			stringBuilder.append(" - ");
			stringBuilder.append(values.get(i).label);
		}

		return stringBuilder.toString();
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

	public static class Option implements Serializable {

		public String label;
		public String name;
		public String value;

		public Option() {
			super();
		}

		public Option(Map<String, String> optionMap) {
			this(optionMap.get("label"), optionMap.get("name"), optionMap.get("value"));
		}

		public Option(String label, String name, String value) {
			this.label = label;
			this.name = name;
			this.value = value;
		}

		@Override
		public int hashCode() {
			int result = label != null ? label.hashCode() : 0;
			result = 31 * result + (name != null ? name.hashCode() : 0);
			result = 31 * result + (value != null ? value.hashCode() : 0);
			return result;
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
				} else {
					return label.equals(opt.label) && value.equals(opt.value);
				}
			}

			return super.equals(obj);
		}
	}
}