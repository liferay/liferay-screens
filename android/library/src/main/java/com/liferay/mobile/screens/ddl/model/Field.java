/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.w3c.dom.Element;

/**
 * @author Jose Manuel Navarro
 */
public abstract class Field<T extends Serializable> implements Parcelable {

	private String text;
	private Map<String, Object> attributes = new HashMap<>();
	private DataType dataType;
	private EditorType editorType;
	private String name;
	private String label;
	private String tip;
	private boolean readOnly;
	private boolean repeatable;
	private boolean required;
	private boolean showLabel;
	private T predefinedValue;
	private T currentValue;
	private boolean lastValidationResult = true;
	private Locale currentLocale;
	private Locale defaultLocale;
	private List<Field> fields = new ArrayList<>();

	public static final int RATE_FIELD = 10000;

	public Field() {
		super();
	}

	public Field(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
		this.currentLocale = currentLocale;
		this.defaultLocale = defaultLocale;
		this.attributes = attributes;

		dataType = DataType.valueOf(attributes);
		editorType = EditorType.valueOf(attributes);

		name = getAttributeStringValue(attributes, "name");
		label = getAttributeStringValue(attributes, "label");
		tip = getAttributeStringValue(attributes, "tip");

		readOnly = Boolean.valueOf(getAttributeStringValue(attributes, "readOnly"));
		repeatable = Boolean.valueOf(getAttributeStringValue(attributes, "repeatable"));
		required = Boolean.valueOf(getAttributeStringValue(attributes, "required"));
		showLabel = Boolean.valueOf(getAttributeStringValue(attributes, "showLabel"));

		String predefinedValue = getAttributeStringValue(attributes, "predefinedValue");
		this.predefinedValue = convertFromString(predefinedValue);
		currentValue = this.predefinedValue;

		String text = getAttributeStringValue(attributes, "text");
		if (!text.isEmpty()) {
			this.text = text;
			currentValue = convertFromString(text);
		}
	}

	protected Field(Parcel in, ClassLoader loader) {
		Parcelable[] array = in.readParcelableArray(getClass().getClassLoader());
		fields = new ArrayList(Arrays.asList(array));

		dataType = DataType.assignDataTypeFromString(in.readString());
		editorType = EditorType.valueOfString(in.readString());

		name = in.readString();
		label = in.readString();
		tip = in.readString();

		readOnly = (in.readInt() == 1);
		repeatable = (in.readInt() == 1);
		required = (in.readInt() == 1);
		showLabel = (in.readInt() == 1);

		predefinedValue = (T) in.readSerializable();
		currentValue = (T) in.readSerializable();

		currentLocale = (Locale) in.readSerializable();
		defaultLocale = (Locale) in.readSerializable();

		lastValidationResult = (in.readInt() == 1);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o instanceof Field) {
			Field field = (Field) o;

			if (name.equals(field.name)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	public String getName() {
		return name;
	}

	public DataType getDataType() {
		return dataType;
	}

	public EditorType getEditorType() {
		return editorType;
	}

	public String getText() {
		return text;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isRepeatable() {
		return repeatable;
	}

	public boolean isRequired() {
		return required;
	}

	public boolean isShowLabel() {
		return showLabel;
	}

	public boolean isValid() {
		boolean valid = !((currentValue == null) && isRequired());

		if (valid) {
			valid = doValidate();
		}

		lastValidationResult = valid;

		return valid;
	}

	public String getLabel() {
		return label;
	}

	public boolean getLastValidationResult() {
		return lastValidationResult;
	}

	public void setLastValidationResult(boolean lastValidationResult) {
		this.lastValidationResult = lastValidationResult;
	}

	public String getTip() {
		return tip;
	}

	public T getPredefinedValue() {
		return predefinedValue;
	}

	public void setPredefinedValue(T value) {
		predefinedValue = value;
	}

	public T getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(T value) {
		currentValue = value;
	}

	public void setCurrentStringValue(String value) {
		setCurrentValue(convertFromString(value));
	}

	public Locale getCurrentLocale() {
		return currentLocale;
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public String toData() {
		return convertToData(currentValue);
	}

	public String toFormattedString() {
		return convertToFormattedString(currentValue);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeParcelableArray(fields.toArray(new Field[fields.size()]), flags);

		destination.writeString(dataType.getValue());
		destination.writeString(editorType.getValue());

		destination.writeString(name);
		destination.writeString(label);
		destination.writeString(tip);

		destination.writeInt(readOnly ? 1 : 0);
		destination.writeInt(repeatable ? 1 : 0);
		destination.writeInt(required ? 1 : 0);
		destination.writeInt(showLabel ? 1 : 0);

		destination.writeSerializable(predefinedValue);
		destination.writeSerializable(currentValue);

		destination.writeSerializable(currentLocale);
		destination.writeSerializable(defaultLocale);

		destination.writeInt(lastValidationResult ? 1 : 0);
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
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

	public enum DataType {
		BOOLEAN("boolean"), STRING("string"), HTML("html"), DATE("date"), NUMBER("number"), IMAGE("image"), DOCUMENT(
			"document-library"), UNSUPPORTED(null);

		private final String value;

		DataType(String value) {
			this.value = value;
		}

		public static DataType assignDataTypeFromString(String stringDataType) {
			if (stringDataType != null) {
				for (DataType dataType : values()) {
					if (stringDataType.equals(dataType.value)) {
						return dataType;
					}
				}

				if ("".equals(stringDataType)) {
					return STRING;
				}

				if ("integer".equals(stringDataType) || "double".equals(stringDataType)) {
					return NUMBER;
				}
			}

			return UNSUPPORTED;
		}

		public static DataType valueOf(Map<String, Object> attributes) {
			Object mapValue = attributes.get("dataType");

			if (mapValue == null) {
				return UNSUPPORTED;
			}

			return assignDataTypeFromString(mapValue.toString());
		}

		public static DataType valueOf(Element element) {
			String attributeValue = element.getAttribute("dataType");

			if (attributeValue.isEmpty()) {
				return UNSUPPORTED;
			}

			return assignDataTypeFromString(attributeValue);
		}

		public Field createField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
			if (STRING.equals(this)) {
				EditorType editor = EditorType.valueOf(attributes);

				if (editor == EditorType.SELECT || editor == EditorType.RADIO) {
					return new StringWithOptionsField(attributes, locale, defaultLocale);
				} else if (editor == EditorType.DATE) {
					return new DateField(attributes, locale, defaultLocale);
				} else {
					return new StringField(attributes, locale, defaultLocale);
				}
			} else if (HTML.equals(this)) {
				return new StringField(attributes, locale, defaultLocale);
			} else if (BOOLEAN.equals(this)) {
				return new BooleanField(attributes, locale, defaultLocale);
			} else if (DATE.equals(this)) {
				return new DateField(attributes, locale, defaultLocale);
			} else if (NUMBER.equals(this)) {
				return new NumberField(attributes, locale, defaultLocale);
			} else if (DOCUMENT.equals(this)) {
				return new DocumentField(attributes, locale, defaultLocale);
			} else if (IMAGE.equals(this)) {
				return new ImageField(attributes, locale, defaultLocale);
			}
			return null;
		}

		public String getValue() {
			return value;
		}

	}

	public enum EditorType {
		CHECKBOX("checkbox"), TEXT("text"), TEXT_AREA("textarea", "paragraph", "ddm-text-html"), DATE("ddm-date",
			"date"), NUMBER("ddm-number", "number", "numeric"), INTEGER("ddm-integer", "integer"), DECIMAL(
			"ddm-decimal", "decimal"), SELECT("select", "checkbox_multiple"), RADIO("radio"), DOCUMENT(
			"ddm-documentlibrary", "documentlibrary", "wcm-image"), UNSUPPORTED("");

		private final String[] values;

		EditorType(String... values) {
			this.values = values;
		}

		public static List<EditorType> all() {
			List<EditorType> editorTypes = new ArrayList<>(Arrays.asList(EditorType.values()));

			editorTypes.remove(UNSUPPORTED);

			return editorTypes;
		}

		public static EditorType valueOf(Map<String, Object> attributes) {
			Object mapValue = attributes.get("type");

			if (mapValue == null) {
				return UNSUPPORTED;
			}

			if ("text".equals(mapValue) && "integer".equals(attributes.get("dataType"))) {
				return DECIMAL;
			}
			return valueOfString(mapValue.toString());
		}

		public static EditorType valueOfString(String name) {

			if (name != null) {
				for (EditorType editorType : values()) {
					for (String value : editorType.values) {
						if (name.equals(value)) {
							return editorType;
						}
					}
				}
			}

			return UNSUPPORTED;
		}

		public String[] getValues() {
			return values;
		}

		public String getValue() {
			return values[0];
		}

	}
}