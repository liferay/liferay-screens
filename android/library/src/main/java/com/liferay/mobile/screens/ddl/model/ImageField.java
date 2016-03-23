package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class ImageField extends StringField implements Parcelable {

	public static final ClassLoaderCreator<ImageField> CREATOR =
		new ClassLoaderCreator<ImageField>() {

			@Override
			public ImageField createFromParcel(Parcel source, ClassLoader loader) {
				return new ImageField(source, loader);
			}

			public ImageField createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public ImageField[] newArray(int size) {
				return new ImageField[size];
			}
		};

	public ImageField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);
	}

	protected ImageField(Parcel in, ClassLoader loader) {
		super(in, loader);
	}
}
