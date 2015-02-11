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

import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public abstract class Field<T> {

	public static enum DataType {
		BOOLEAN("boolean"),
		STRING("string"),
		DATE("date"),
		NUMBER("number"),
		UNSUPPORTED(null);

		private DataType(String value) {
			_value = value;
		}

		public static DataType valueOf(Map<String,Object> attributes) {
			Object mapValue = attributes.get("dataType");
			return (mapValue == null) ?
				UNSUPPORTED : valueOfString(mapValue.toString());
		}

		public static DataType valueOf(Element element) {
			String attributeValue = element.getAttribute("dataType");
			return (attributeValue == null) ?
				UNSUPPORTED : valueOfString(attributeValue);
		}

		public static DataType valueOfString(String name) {
			DataType result = UNSUPPORTED;

			if (name != null) {
				for (DataType dataType : values()) {
					if (name.equals(dataType._value)) {
						return dataType;
					}
				}

				if (name.equals("integer") || name.equals("double")) {
					return NUMBER;
				}
			}

			return result;
		}

		public Field createField(Map<String,Object> attributes, Locale locale) {
			if (this.equals(STRING)) {
				EditorType editor = EditorType.valueOf(attributes);

				if (editor == EditorType.SELECT || editor == EditorType.RADIO) {
					return new StringWithOptionsField(attributes, locale);
				}
				else {
					return new StringField(attributes, locale);
				}
			}
			else if (this.equals(BOOLEAN)) {
				return new BooleanField(attributes, locale);
			}
			else if (this.equals(DATE)) {
				return new DateField(attributes, locale);
			}
			else if (this.equals(NUMBER)) {
				return new NumberField(attributes, locale);
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		private String _value;
	}


	public static enum EditorType {
		CHECKBOX("checkbox"),
		TEXT("text"),
		DATE("ddm-date"),
		NUMBER("ddm-number"),
		SELECT("select"),
		RADIO("radio"),
		UNSUPPORTED(null);

		private EditorType(String value) {
			_value = value;
		}

		public static List<EditorType> all() {
			List<EditorType> editorTypes = new ArrayList<>(
				Arrays.asList(EditorType.values()));

			editorTypes.remove(UNSUPPORTED);

			return editorTypes;
		}

		public static EditorType valueOf(Map<String,Object> attributes) {
			Object mapValue = attributes.get("type");
			return (mapValue == null) ?
				UNSUPPORTED : valueOfString(mapValue.toString());
		}

		public static EditorType valueOfString(String name) {
			EditorType result = UNSUPPORTED;

			if (name != null) {
				for (EditorType editorType : values()) {
					if (name.equals(editorType._value)) {
						return editorType;
					}
				}

				if (name.equals("ddm-integer") || name.equals("ddm-decimal")) {
					return NUMBER;
				}
			}

			return result;
		}

		public String getValue() {
			return _value;
		}

		private String _value;

	}



	public Field(Map<String,Object> attributes, Locale locale) {
		_dataType = DataType.valueOf(attributes);
		_editorType = EditorType.valueOf(attributes);

		_name = getAttributeStringValue(attributes, "name");
		_label = getAttributeStringValue(attributes, "label");
		_tip = getAttributeStringValue(attributes, "tip");

		_readOnly = Boolean.valueOf(getAttributeStringValue(attributes, "readOnly"));
		_repeatable = Boolean.valueOf(getAttributeStringValue(attributes, "repeatable"));
		_required = Boolean.valueOf(getAttributeStringValue(attributes, "required"));
		_showLabel = Boolean.valueOf(getAttributeStringValue(attributes, "showLabel"));

		_predefinedValue = convertFromString(
			getAttributeStringValue(attributes, "predefinedValue"));
		_currentValue = _predefinedValue;

		_currentLocale = locale;
	}

	public String getName() {
		return _name;
	}

	public DataType getDataType() {
		return _dataType;
	}

	public EditorType getEditorType() {
		return _editorType;
	}

	public boolean isReadOnly() {
		return _readOnly;
	}

	public boolean isRepeatable() {
		return _repeatable;
	}

	public boolean isRequired() {
		return _required;
	}

	public boolean isShowLabel() {
		return _showLabel;
	}

	public String getLabel() {
		return _label;
	}

	public String getTip() {
		return _tip;
	}

	public T getPredefinedValue() {
		return _predefinedValue;
	}

	public T getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(T value) {
		_currentValue = value;
	}

	public Locale getCurrentLocale() {
		return _currentLocale;
	}

	public String toData() {
		return convertToData(_currentValue);
	}

	public String toFormattedString() {
		return convertToFormattedString(_currentValue);
	}

	protected String getAttributeStringValue(Map<String,Object> attributes, String key) {
		Object value = attributes.get(key);
		return (value != null) ? value.toString() : "";
	}

	protected abstract T convertFromString(String stringValue);

	protected abstract String convertToData(T value);

	protected abstract String convertToFormattedString(T value);

	private DataType _dataType;
	private EditorType _editorType;

	private String _name;
	private String _label;
	private String _tip;

	private boolean _readOnly;
	private boolean _repeatable;
	private boolean _required;
	private boolean _showLabel;

	private T _predefinedValue;
	private T _currentValue;

	private Locale _currentLocale;

}
