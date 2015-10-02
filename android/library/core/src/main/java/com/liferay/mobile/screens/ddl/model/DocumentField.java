package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class DocumentField extends Field<DocumentFile> {

	public static final Parcelable.Creator<DocumentField> CREATOR =
		new Parcelable.Creator<DocumentField>() {

			public DocumentField createFromParcel(Parcel in) {
				return new DocumentField(in);
			}

			public DocumentField[] newArray(int size) {
				return new DocumentField[size];
			}
		};


	private enum State {
		IDLE,
		UPLOADING,
		UPLOADED,
		FAILED
	}

	public DocumentField(Map<String, Object> attributes, Locale locale) {
		super(attributes, locale);
	}

	protected DocumentField(Parcel in) {
		super(in);
	}

	public boolean moveToUploadInProgressState() {
		if (_state == State.IDLE || _state == State.FAILED || _state == State.UPLOADED) {
			_state = State.UPLOADING;
			return true;
		}

		return false;
	}

	public boolean moveToUploadCompleteState() {
		if (_state == State.UPLOADING) {
			_state = State.UPLOADED;
			return true;
		}

		return false;
	}

	public boolean moveToUploadFailureState() {
		if (_state == State.UPLOADING) {
			_state = State.FAILED;
			return true;
		}

		return false;
	}

	public boolean isUploaded() {
		return (_state == State.UPLOADED);
	}

	public boolean isUploading() {
		return (_state == State.UPLOADING);
	}

	public boolean isUploadFailed() {
		return (_state == State.FAILED);
	}

	public void createLocalFile(String path) {
		if (path == null || path.isEmpty()) {
			return;
		}

		setCurrentValue(new DocumentLocalFile(path));
	}

	@Override
	protected DocumentFile convertFromString(String string) {
		if (string == null || string.isEmpty()) {
			return null;
		}

		DocumentRemoteFile result = null;

		try {
			result = new DocumentRemoteFile(string);
		}
		catch (JSONException e) {
			Log.e("liferay-screens", "Can't parse the document JSON", e);
		}

		return result;
	}

	@Override
	protected String convertToData(DocumentFile document) {
		return document == null ? null : document.toData();
	}

	@Override
	protected String convertToFormattedString(DocumentFile document) {
		return document == null ? "" : document.toString();
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		DocumentFile currentValue = getCurrentValue();

		if (currentValue == null) {
			if (isRequired()) {
				valid = false;
			}
		}
		else {
			if (!currentValue.isValid()) {
				valid = false;
			}
		}

		if (isUploading()) {
			valid = false;
		}
		else if (isUploadFailed()) {
			valid = false;
		}

		return valid;
	}
	private State _state = State.IDLE;

}
