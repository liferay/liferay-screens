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
public class SelectableOptionsField extends OptionsField<ArrayList<Option>> {

	public static final Parcelable.ClassLoaderCreator<SelectableOptionsField> CREATOR =
		new Parcelable.ClassLoaderCreator<SelectableOptionsField>() {

			@Override
			public SelectableOptionsField createFromParcel(Parcel source, ClassLoader loader) {
				return new SelectableOptionsField(source, loader);
			}

			public SelectableOptionsField createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public SelectableOptionsField[] newArray(int size) {
				return new SelectableOptionsField[size];
			}
		};
	private boolean multiple;
	private DataProvider dataProvider;

	public SelectableOptionsField() {
		super();
	}

	public SelectableOptionsField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);

		Object multipleValue = attributes.get(FormFieldKeys.MULTIPLE);
		multiple = (multipleValue != null) ? Boolean.valueOf(multipleValue.toString()) : false;
		if ("checkbox_multiple".equals(attributes.get("type"))) {
			multiple = true;
		}

		ArrayList<Option> predefinedOptions = convertFromString(getAttributeStringValue(attributes, "predefinedValue"));

		setPredefinedValue(predefinedOptions);
		setCurrentValue(predefinedOptions);
	}

	protected SelectableOptionsField(Parcel in, ClassLoader loader) {
		super(in, loader);

		multiple = in.readInt() == 1;
		dataProvider = (DataProvider) in.readSerializable();
	}

	public DataProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DataProvider dataProvider) {
		this.dataProvider = dataProvider;
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

		destination.writeInt(multiple ? 1 : 0);
		destination.writeSerializable(dataProvider);
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

	public static class DataProvider implements Serializable {

		public String url;
		public String username;
		public String password;
		public String name;
		public String value;

		public DataProvider(String url, String username, String password, String name, String value) {
			this.url = url;
			this.username = username;
			this.password = password;
			this.name = name;
			this.value = value;
		}
	}
}
