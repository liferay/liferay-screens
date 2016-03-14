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

import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jose Manuel Navarro
 */
public abstract class Field<T extends Serializable> implements Parcelable {

	public enum DataType {
		BOOLEAN("boolean"),
		STRING("string"),
		DATE("date"),
		NUMBER("number"),
		DOCUMENT("document-library"),
		UNSUPPORTED(null);

		public static DataType valueOf(Map<String, Object> attributes) {
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

		public Field createField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
			if (STRING.equals(this)) {
				EditorType editor = EditorType.valueOf(attributes);

				if (editor == EditorType.SELECT || editor == EditorType.RADIO) {
					return new StringWithOptionsField(attributes, locale, defaultLocale);
				}
				else {
					return new StringField(attributes, locale, defaultLocale);
				}
			}
			else if (BOOLEAN.equals(this)) {
				return new BooleanField(attributes, locale, defaultLocale);
			}
			else if (DATE.equals(this)) {
				return new DateField(attributes, locale, defaultLocale);
			}
			else if (NUMBER.equals(this)) {
				return new NumberField(attributes, locale, defaultLocale);
			}
			else if (DOCUMENT.equals(this)) {
				return new DocumentField(attributes, locale, defaultLocale);
			}
			return null;
		}

		public String getValue() {
			return _value;
		}

		DataType(String value) {
			_value = value;
		}

		private String _value;

	}


	public enum EditorType {
		CHECKBOX("checkbox"),
		TEXT("text"),
		TEXT_AREA("textarea"),
		DATE("ddm-date"),
		NUMBER("ddm-number"),
		INTEGER("ddm-integer"),
		DECIMAL("ddm-decimal"),
		SELECT("select"),
		RADIO("radio"),
		DOCUMENT("ddm-documentlibrary"),
		UNSUPPORTED(null);

		public static List<EditorType> all() {
			List<EditorType> editorTypes = new ArrayList<>(Arrays.asList(EditorType.values()));

			editorTypes.remove(UNSUPPORTED);

			return editorTypes;
		}

		public static EditorType valueOf(Map<String, Object> attributes) {
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
			}

			return result;
		}

		public String getValue() {
			return _value;
		}

		EditorType(String value) {
			_value = value;
		}

		private String _value;

	}


	public Field(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
		_currentLocale = currentLocale;
		_defaultLocale = defaultLocale;

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
	}

	protected Field(Parcel in) {
		_dataType = DataType.valueOfString(in.readString());
		_editorType = EditorType.valueOfString(in.readString());

		_name = in.readString();
		_label = in.readString();
		_tip = in.readString();

		_readOnly = (in.readInt() == 1);
		_repeatable = (in.readInt() == 1);
		_required = (in.readInt() == 1);
		_showLabel = (in.readInt() == 1);

		_predefinedValue = (T) in.readSerializable();
		_currentValue = (T) in.readSerializable();

		_currentLocale = (Locale) in.readSerializable();
		_defaultLocale = (Locale) in.readSerializable();

		_lastValidationResult = (in.readInt() == 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Field) {
			Field field = (Field) o;

			if (_name.equals(field._name)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
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

	public boolean isValid() {
		boolean valid = !((_currentValue == null) && isRequired());

		if (valid) {
			valid = doValidate();
		}

		_lastValidationResult = valid;

		return valid;
	}

	public String getLabel() {
		return _label;
	}

	public boolean getLastValidationResult() {
		return _lastValidationResult;
	}

	public void setLastValidationResult(boolean lastValidationResult) {
		_lastValidationResult = lastValidationResult;
	}

	public String getTip() {
		return _tip;
	}

	public T getPredefinedValue() {
		return _predefinedValue;
	}

	public void setPredefinedValue(T value) {
		_predefinedValue = value;
	}

	public T getCurrentValue() {
		return _currentValue;
	}

	public void setCurrentValue(T value) {
		_currentValue = value;
	}

	public void setCurrentStringValue(String value) {
		setCurrentValue(convertFromString(value));
	}

	public Locale getCurrentLocale() {
		return _currentLocale;
	}

	public Locale getDefaultLocale() {
		return _defaultLocale;
	}

	public String toData() {
		return convertToData(_currentValue);
	}

	public String toFormattedString() {
		return convertToFormattedString(_currentValue);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeString(_dataType.getValue());
		destination.writeString(_editorType.getValue());

		destination.writeString(_name);
		destination.writeString(_label);
		destination.writeString(_tip);

		destination.writeInt(_readOnly ? 1 : 0);
		destination.writeInt(_repeatable ? 1 : 0);
		destination.writeInt(_required ? 1 : 0);
		destination.writeInt(_showLabel ? 1 : 0);

		destination.writeSerializable(_predefinedValue);
		destination.writeSerializable(_currentValue);

		destination.writeSerializable(_currentLocale);
		destination.writeSerializable(_defaultLocale);

		destination.writeInt(_lastValidationResult ? 1 : 0);
	}


	protected String getAttributeStringValue(Map<String, Object> attributes, String key) {
		Object value = attributes.get(key);
		return (value != null) ? value.toString() : "";
	}

	protected abstract T convertFromString(String stringValue);

	protected abstract String convertToData(T value);

	protected abstract String convertToFormattedString(T value);

	protected boolean doValidate() {
		return true;
	}

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

	private boolean _lastValidationResult = true;

	private Locale _currentLocale;
	private Locale _defaultLocale;

}