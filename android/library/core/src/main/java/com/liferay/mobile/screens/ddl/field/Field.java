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

import org.w3c.dom.Element;

import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public abstract class Field<T> {

	public static enum DataType {
		BOOLEAN("boolean"),
		STRING("string"),
		UNSUPPORTED(null);

		private DataType(String value) {
			_value = value;
		}

		public static DataType valueOf(Map<String,String> attributes) {
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
					if (dataType._value.equals(name)) {
						return dataType;
					}
				}
			}

			return result;
		}

		public Field createField(Map<String,String> attributes, Locale locale) {
			if (this.equals(STRING)) {
				return new StringField(attributes, locale);
			}
			else if  (this.equals(BOOLEAN)) {
				return new BooleanField(attributes, locale);
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
		UNSUPPORTED(null);

		private EditorType(String value) {
			_value = value;
		}

		public static EditorType valueOf(Map<String,String> attributes) {
			Object mapValue = attributes.get("type");
			return (mapValue == null) ?
				UNSUPPORTED : valueOfString(mapValue.toString());
		}

		public static EditorType valueOfString(String name) {
			EditorType result = UNSUPPORTED;

			if (name != null) {
				for (EditorType editorType : values()) {
					if (editorType._value.equals(name)) {
						return editorType;
					}
				}
			}

			return result;
		}

		public String getValue() {
			return _value;
		}

		private String _value;

	}



	public Field(Map<String,String> attributes, Locale locale) {
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

	@Override
	public String toString() {
		return convertToString(_currentValue);
	}

	public String toLabel() {
		return convertToLabel(_currentValue);
	}

	protected String getAttributeStringValue(Map<String,String> attributes, String key) {
		Object value = attributes.get(key);
		return (value != null) ? value.toString() : "";
	}

	protected abstract T convertFromString(String stringValue);

	protected abstract String convertToString(T value);

	protected abstract String convertToLabel(T value);

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
