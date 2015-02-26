package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.liferay.mobile.screens.util.ViewUtil;

import java.io.Serializable;
import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class DocumentField extends Field<DocumentField.File> {

	public static final Parcelable.Creator<DocumentField> CREATOR =
			new Parcelable.Creator<DocumentField>() {

				public DocumentField createFromParcel(Parcel in) {
					return new DocumentField(in);
				}

				public DocumentField[] newArray(int size) {
					return new DocumentField[size];
				}
			};


	public DocumentField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);
	}

	public enum State {
		PENDING, UPLOADING, LOADED, ERROR;
	}

	public static class File implements Serializable {

		public File(String name, Integer id, State state) {
			_name = name;
			_id = id;
			_state = state;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) {
				return false;
			}

			if (obj instanceof File) {
				File file = (File) obj;

				return _name.equals(file.getName()) && _id.equals(file.getId());
			}

			return super.equals(obj);
		}

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		public Integer getId() {
			return _id;
		}

		public State getState() {
			return _state;
		}

		public void setState(State state) {
			_state = state;
		}

		private String _name;
		private Integer _id;
		private State _state;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof DocumentField) {
			DocumentField other = (DocumentField) o;
			return getCurrentValue().equals(other.getCurrentValue());
		}
		return false;
	}

	protected DocumentField(Parcel in) {
		super(in);
	}

	@Override
	protected File convertFromString(String name) {
		return new File(name, ViewUtil._generateUniqueId(), null);
	}

	@Override
	protected String convertToData(File file) {
		return file.getName();
	}

	@Override
	protected String convertToFormattedString(File file) {
		return file.getName();
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		File currentValue = getCurrentValue();

		if (currentValue != null && isRequired()) {
			String trimmedString = currentValue.getName().trim();
			valid = !trimmedString.isEmpty();
		}

		return valid;
	}

}
