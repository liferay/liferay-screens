package com.liferay.mobile.screens.ddm.form.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Paulo Cruz
 */
public class DDMStringField extends DDMField<String> {

    public static final Parcelable.ClassLoaderCreator<DDMStringField> CREATOR =
            new Parcelable.ClassLoaderCreator<DDMStringField>() {

                @Override
                public DDMStringField createFromParcel(Parcel source, ClassLoader loader) {
                    return new DDMStringField(source, loader);
                }

                public DDMStringField createFromParcel(Parcel in) {
                    throw new AssertionError();
                }

                public DDMStringField[] newArray(int size) {
                    return new DDMStringField[size];
                }
            };

    public DDMStringField(Map<String, Object> attributes, Locale currentLocale, Locale defaultLocale) {
        super(attributes, currentLocale, defaultLocale);
    }

    protected DDMStringField(Parcel source, ClassLoader loader) {
        super(source, loader);
    }

    @Override
    protected String convertFromString(String stringValue) {
        return stringValue;
    }

    @Override
    protected String convertToData(String value) {
        return value;
    }

    @Override
    protected String convertToFormattedString(String value) {
        return value;
    }
}
