package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

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

	@Override
	protected DocumentFile convertFromString(String string) {
		if (!string.isEmpty()) {
			return new RemoteFile(string);
		}
		return new LocalFile("");
	}

	public void createLocalFile(String path) {
		setCurrentValue(new LocalFile(path));
	}

	@Override
	protected String convertToData(DocumentFile document) {
		return document.toData();
	}

	@Override
	protected String convertToFormattedString(DocumentFile document) {
		return document.toString();
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		if (getCurrentValue() == null && isRequired()) {
			valid = false;
		}

		return valid;
	}



	private enum State {
		IDLE,
		UPLOADING,
		UPLOADED,
		FAILED;
	}

	private State _state = State.IDLE;

}
