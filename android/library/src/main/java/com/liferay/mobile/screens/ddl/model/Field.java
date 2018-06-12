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
import com.liferay.mobile.screens.ddm.form.model.CheckboxMultipleField;
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
	//dataSourceType
	private DataType dataType;
	//additionalType
	private EditorType editorType;
	private String name;
	private String label;
	private String tip;
	private String placeHolder;
	private boolean readOnly;
	private boolean repeatable;
	private boolean required;
	private boolean showLabel;
	private boolean hasFormRules;
	private T predefinedValue;
	private T currentValue;
	private boolean lastValidationResult = true;
	private Locale currentLocale;
	private Locale defaultLocale;
	private String visibilityExpression;
	private String ddmDataProviderInstance;
	private List<Field> fields = new ArrayList<>();
	private Map<String, Object> attributes = new HashMap<>();

	private boolean autocomplete = false;
	private boolean localizable = false;
	private boolean isTransient = false;
	private String style;
	private String displayStyle;
	private String indexType;
	public Field() {
		super();
	}

	public String getVisibilityExpression() {
		return visibilityExpression;
	}

	public void setVisibilityExpression(String visibilityExpression) {
		this.visibilityExpression = visibilityExpression;
	}

	public String getDdmDataProviderInstance() {
		return ddmDataProviderInstance;
	}

	public void setDdmDataProviderInstance(String ddmDataProviderInstance) {
		this.ddmDataProviderInstance = ddmDataProviderInstance;
	}

	public Field(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
		this.currentLocale = currentLocale;
		this.defaultLocale = defaultLocale;
		this.attributes = attributes;

		dataType = DataType.valueOf(attributes);
		editorType = EditorType.valueOf(attributes);
		displayStyle = getAttributeStringValue(attributes, FormFieldKeys.DISPLAY_STYLE);

		name = getAttributeStringValue(attributes, FormFieldKeys.NAME);
		label = getAttributeStringValue(attributes, FormFieldKeys.LABEL);
		tip = getAttributeStringValue(attributes, FormFieldKeys.TIP);
		placeHolder = getAttributeStringValue(attributes, FormFieldKeys.PLACEHOLDER);

		readOnly = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.READ_ONLY));
		repeatable = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.REPEATABLE));
		required = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.REQUIRED));
		showLabel = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.SHOW_LABEL));
		hasFormRules = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.HAS_FORM_RULES));
		visibilityExpression = getAttributeStringValue(attributes, FormFieldKeys.VISIBILITY_EXPRESSION);
		ddmDataProviderInstance = getAttributeStringValue(attributes, FormFieldKeys.DDM_DATA_PROVIDER_INSTANCE);

		autocomplete = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_AUTOCOMPLETE));

		String predefinedValue = getAttributeStringValue(attributes, FormFieldKeys.PREDEFINED_VALUE);
		this.predefinedValue = convertFromString(predefinedValue);
		currentValue = this.predefinedValue;

		String text = getAttributeStringValue(attributes, FormFieldKeys.TEXT);
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
		displayStyle = in.readString();

		name = in.readString();
		label = in.readString();
		tip = in.readString();
		placeHolder = in.readString();

		readOnly = (in.readInt() == 1);
		repeatable = (in.readInt() == 1);
		required = (in.readInt() == 1);
		showLabel = (in.readInt() == 1);
		hasFormRules = (in.readInt() == 1);

		predefinedValue = (T) in.readSerializable();
		currentValue = (T) in.readSerializable();

		currentLocale = (Locale) in.readSerializable();
		defaultLocale = (Locale) in.readSerializable();

		lastValidationResult = (in.readInt() == 1);
		visibilityExpression = in.readString();
		ddmDataProviderInstance = in.readString();

        autocomplete = (in.readInt() == 1);
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


	public boolean isAutocomplete() {
		return autocomplete;
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

	public void setRequired(boolean required) {
		this.required = required;
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

	public String getPlaceHolder() {
		return placeHolder;
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

	public boolean hasFormRules() {
		return hasFormRules;
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
		destination.writeString(displayStyle);

		destination.writeString(name);
		destination.writeString(label);
		destination.writeString(tip);
		destination.writeString(placeHolder);

		destination.writeInt(readOnly ? 1 : 0);
		destination.writeInt(repeatable ? 1 : 0);
		destination.writeInt(required ? 1 : 0);
		destination.writeInt(showLabel ? 1 : 0);
		destination.writeInt(hasFormRules ? 1 : 0);

		destination.writeSerializable(predefinedValue);
		destination.writeSerializable(currentValue);

		destination.writeSerializable(currentLocale);
		destination.writeSerializable(defaultLocale);

		destination.writeInt(lastValidationResult ? 1 : 0);
		destination.writeString(visibilityExpression);
		destination.writeString(ddmDataProviderInstance);

        destination.writeInt(autocomplete ? 1 : 0);
    }

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
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
			"document-library"), GEO("geolocation"), UNSUPPORTED(null);

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
					return new SelectableOptionsField(attributes, locale, defaultLocale);
				} else if (editor == EditorType.DATE) {
					return new DateField(attributes, locale, defaultLocale);
				} else if (editor == EditorType.DOCUMENT) {
					return new DocumentField(attributes, locale, defaultLocale);
				} else if (editor == EditorType.CHECKBOX_MULTIPLE) {
					return new CheckboxMultipleField(attributes, locale, defaultLocale);
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
			} else if (GEO.equals(this)) {
				return new GeolocationField(attributes, locale, defaultLocale);
			} else {
				if (EditorType.valueOf(attributes) == EditorType.PARAGRAPH) {
					return new StringField(attributes, locale, defaultLocale);
				}
			}
			return null;
		}

		public String getValue() {
			return value;
		}

	}

	public enum EditorType {
		CHECKBOX("checkbox"), TEXT("text"), TEXT_AREA("textarea", "ddm-text-html"), PARAGRAPH("paragraph"), DATE(
			"ddm-date", "date"), NUMBER("ddm-number", "number", "numeric"), INTEGER("ddm-integer", "integer"), DECIMAL(
			"ddm-decimal", "decimal", "double"), SELECT("select"), CHECKBOX_MULTIPLE("checkbox_multiple"), RADIO(
			"radio"), DOCUMENT("ddm-documentlibrary", "document_library", "documentlibrary", "wcm-image"), GEO(
			"ddm-geolocation", "geolocation"), UNSUPPORTED("");

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

			if ("text".equals(mapValue) && "multiline".equals(attributes.get("displayStyle"))) {
				return TEXT_AREA;
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
