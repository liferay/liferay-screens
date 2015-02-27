package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class DocumentField extends Field<String> {

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
		PENDING, UPLOADING, UPLOADED, FAILED;
	}

	protected DocumentField(Parcel in) {
		super(in);
	}

	@Override
	protected String convertFromString(String name) {
		return name;
	}

	@Override
	protected String convertToData(String fileName) {
		// TODO build here the JSON to link the field with the document already uploaded
		return fileName;
	}

	@Override
	protected String convertToFormattedString(String fileName) {
		return fileName;
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		if (getCurrentValue() == null && isRequired()) {
			valid = false;
		}

		return valid;
	}

	public State getState() {
		return _state;
	}

	public void setState(State state) {
		_state = state;
	}

	private State _state;

}
