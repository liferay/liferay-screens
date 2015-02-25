package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class FileField extends Field<FileField.File> {

	public static final Parcelable.Creator<FileField> CREATOR =
			new Parcelable.Creator<FileField>() {

				public FileField createFromParcel(Parcel in) {
					return new FileField(in);
				}

				public FileField[] newArray(int size) {
					return new FileField[size];
				}
			};


	public FileField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);
	}

	public enum State {
		PENDING, UPLOADING, LOADED;
	}

	public static class File implements Serializable {

		public File(String name, State state) {
			this.name = name;
			this.state = state;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (obj instanceof File) {
				File file = (File) obj;

				return name.equals(file.name);
			}

			return super.equals(obj);
		}

		public String name;
		public State state;
	}

	protected FileField(Parcel in) {
		super(in);
	}

	@Override
	protected File convertFromString(String name) {
		return new File(name, State.PENDING);
	}

	@Override
	protected String convertToData(File file) {
		return file.name;
	}

	@Override
	protected String convertToFormattedString(File file) {
		return file.name;
	}

	@Override
	protected  boolean doValidate() {
		boolean valid = true;

		File currentValue = getCurrentValue();

		if (currentValue != null && isRequired()) {
			String trimmedString = currentValue.name.trim();

			valid = !trimmedString.equals("");
		}

		return valid;
	}

}
