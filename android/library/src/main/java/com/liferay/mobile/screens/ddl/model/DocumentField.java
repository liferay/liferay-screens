package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;

/**
 * @author Javier Gamarra
 */
public class DocumentField extends Field<DocumentFile> {

	public static final Parcelable.ClassLoaderCreator<DocumentField> CREATOR =
		new Parcelable.ClassLoaderCreator<DocumentField>() {

			@Override
			public DocumentField createFromParcel(Parcel parcel, ClassLoader classLoader) {
				return new DocumentField(parcel, classLoader);
			}

			public DocumentField createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public DocumentField[] newArray(int size) {
				return new DocumentField[size];
			}
		};
	private State state = State.IDLE;

	public DocumentField() {
		super();
	}

	public DocumentField(Map<String, Object> attributes, Locale locale, Locale defaultLocale) {
		super(attributes, locale, defaultLocale);
	}

	protected DocumentField(Parcel in, ClassLoader classLoader) {
		super(in, classLoader);
	}

	public boolean moveToUploadInProgressState() {
		if (state == State.IDLE || state == State.FAILED || state == State.UPLOADED) {
			state = State.UPLOADING;
			return true;
		}

		return false;
	}

	public boolean moveToUploadCompleteState() {
		if (state == State.UPLOADING) {
			state = State.UPLOADED;
			return true;
		}

		return false;
	}

	public boolean moveToUploadFailureState() {
		if (state == State.UPLOADING) {
			state = State.FAILED;
			return true;
		}

		return false;
	}

	public boolean isUploaded() {
		return (state == State.UPLOADED);
	}

	public boolean isUploading() {
		return (state == State.UPLOADING);
	}

	public boolean isUploadFailed() {
		return (state == State.FAILED);
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
		} catch (JSONException e) {
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
		return document == null ? "" : document.getFileName();
	}

	@Override
	protected boolean doValidate() {
		boolean valid = true;

		DocumentFile currentValue = getCurrentValue();

		if (currentValue == null) {
			if (isRequired()) {
				valid = false;
			}
		} else {
			if (!currentValue.isValid()) {
				valid = false;
			}
		}

		if (isUploading()) {
			valid = false;
		} else if (isUploadFailed()) {
			valid = false;
		}

		return valid;
	}

	private enum State {
		IDLE, UPLOADING, UPLOADED, FAILED
	}
}
