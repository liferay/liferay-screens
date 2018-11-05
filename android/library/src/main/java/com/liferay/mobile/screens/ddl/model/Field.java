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
import com.liferay.mobile.screens.ddl.form.util.FormFieldKeys;
import com.liferay.mobile.screens.ddm.form.model.CheckboxMultipleField;
import com.liferay.mobile.screens.ddm.form.model.GridField;
import com.liferay.mobile.screens.ddm.form.model.RepeatableField;
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
    private String dataSourceType;
    private DataType dataType;
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

    private boolean isTransient = false;
    private String displayStyle;

    public Field() {
        super();
    }

    public String getDdmDataProviderInstance() {
        return ddmDataProviderInstance;
    }

    public Field(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
        this.currentLocale = currentLocale;
        this.defaultLocale = defaultLocale;
        this.attributes = attributes;

        dataType = DataType.valueOf(attributes);
        editorType = EditorType.valueOf(attributes);
        displayStyle = getAttributeStringValue(attributes, FormFieldKeys.DISPLAY_STYLE_KEY);

        name = getAttributeStringValue(attributes, FormFieldKeys.NAME_KEY);
        label = getAttributeStringValue(attributes, FormFieldKeys.LABEL_KEY);
        Object tipValue = FormFieldKeys.getValueFromArrayKey(attributes, FormFieldKeys.TIP_KEY);
        tip = (tipValue != null) ? tipValue.toString() : "";
        placeHolder = getAttributeStringValue(attributes, FormFieldKeys.PLACE_HOLDER_KEY);

        readOnly = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_READ_ONLY_KEY));
        repeatable = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_REPEATABLE_KEY));
        required = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_REQUIRED_KEY));
        showLabel = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_SHOW_LABEL_KEY));
        hasFormRules = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.HAS_FORM_RULES_KEY));
        visibilityExpression = getAttributeStringValue(attributes, FormFieldKeys.VISIBILITY_EXPRESSION_KEY);
        ddmDataProviderInstance = getAttributeStringValue(attributes, FormFieldKeys.DDM_DATA_PROVIDER_INSTANCE_KEY);

        isTransient = Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_TRANSIENT_KEY));
        dataSourceType = getAttributeStringValue(attributes, FormFieldKeys.DATA_SOURCE_TYPE_KEY);

        String predefinedValue = getAttributeStringValue(attributes, FormFieldKeys.PREDEFINED_VALUE_KEY);
        this.predefinedValue = convertFromString(predefinedValue);
        currentValue = this.predefinedValue;

        String text = getAttributeStringValue(attributes, FormFieldKeys.TEXT_KEY);
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

        isTransient = (in.readInt() == 1);
        dataSourceType = in.readString();
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

    protected void setName(String name) {
        this.name = name;
    }

    public String getDataSourceType() {
        return dataSourceType;
    }

    public DataType getDataType() {
        return dataType;
    }

    public EditorType getEditorType() {
        return editorType;
    }

    protected void setEditorType(EditorType editorType) {
        this.editorType = editorType;
    }

    public String getText() {
        return text;
    }

    public boolean isTransient() {
        return isTransient;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setShowLabel(boolean showLabel) {
        this.showLabel = showLabel;
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

        destination.writeInt(isTransient ? 1 : 0);
        destination.writeString(dataSourceType);
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

    protected static String getAttributeStringValue(Map<String, Object> attributes, String key) {
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
            Object mapValue = attributes.get(FormFieldKeys.DATA_TYPE_KEY);

            if (mapValue == null) {
                return UNSUPPORTED;
            }

            return assignDataTypeFromString(mapValue.toString());
        }

        public static DataType valueOf(Element element) {
            String attributeValue = element.getAttribute(FormFieldKeys.DATA_TYPE_KEY);

            if (attributeValue.isEmpty()) {
                return UNSUPPORTED;
            }

            return assignDataTypeFromString(attributeValue);
        }

        public Field createField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {

            return createField(attributes, locale, defaultLocale, false);
        }

        public Field createField(Map<String, Object> attributes, Locale locale, Locale defaultLocale,
            boolean repeatedField) {

            Field field = null;

            if (STRING.equals(this)) {
                EditorType editor = EditorType.valueOf(attributes);

                if (editor == EditorType.SELECT || editor == EditorType.RADIO) {
                    field = new SelectableOptionsField(attributes, locale, defaultLocale);
                } else if (editor == EditorType.DATE) {
                    field = new DateField(attributes, locale, defaultLocale);
                } else if (editor == EditorType.DOCUMENT) {
                    field = new DocumentField(attributes, locale, defaultLocale);
                } else if (editor == EditorType.CHECKBOX_MULTIPLE) {
                    field = new CheckboxMultipleField(attributes, locale, defaultLocale);
                } else if (editor == EditorType.GRID) {
                    field = new GridField(attributes, locale, defaultLocale);
                } else {
                    field = new StringField(attributes, locale, defaultLocale);
                }
            } else if (HTML.equals(this)) {
                field = new StringField(attributes, locale, defaultLocale);
            } else if (BOOLEAN.equals(this)) {
                field = new BooleanField(attributes, locale, defaultLocale);
            } else if (DATE.equals(this)) {
                field = new DateField(attributes, locale, defaultLocale);
            } else if (NUMBER.equals(this)) {
                field = new NumberField(attributes, locale, defaultLocale);
            } else if (DOCUMENT.equals(this)) {
                field = new DocumentField(attributes, locale, defaultLocale);
            } else if (IMAGE.equals(this)) {
                field = new ImageField(attributes, locale, defaultLocale);
            } else if (GEO.equals(this)) {
                field = new GeolocationField(attributes, locale, defaultLocale);
            } else {
                if (EditorType.valueOf(attributes) == EditorType.PARAGRAPH) {
                    field = new StringField(attributes, locale, defaultLocale);
                }
            }

            if (field != null && !repeatedField) {
                boolean repeatable =
                    Boolean.valueOf(getAttributeStringValue(attributes, FormFieldKeys.IS_REPEATABLE_KEY));

                if (repeatable) {
                    Field baseField = field;
                    field = new RepeatableField(baseField);
                }
            }

            return field;
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
            "ddm-geolocation", "geolocation"), GRID("grid"), REPEATABLE("repeatable"), UNSUPPORTED("");

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
            Object mapValue = FormFieldKeys.getValueFromArrayKey(attributes, FormFieldKeys.ADDITIONAL_TYPE_KEY);

            if (mapValue == null) {
                return UNSUPPORTED;
            }

            if ("text".equals(mapValue) && "integer".equals(attributes.get(FormFieldKeys.DATA_TYPE_KEY))) {
                return DECIMAL;
            }

            if ("text".equals(mapValue) && "multiline".equals(attributes.get(FormFieldKeys.DISPLAY_STYLE_KEY))) {
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
