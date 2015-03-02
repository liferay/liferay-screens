package com.liferay.mobile.screens.ddl.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

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
		PENDING, UPLOADING, UPLOADED, FAILED;
	}

	public static class File implements Serializable {

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		public State getState() {
			return _state;
		}

		public void setState(State state) {
			_state = state;
		}

		public String getUUID() {
			return _uuid;
		}

		public void setUUID(String uuid) {
			_uuid = uuid;
		}

		public Integer getGroupId() {
			return _groupId;
		}

		public void setGroupId(Integer groupId) {
			_groupId = groupId;
		}

		public Integer getVersion() {
			return _version;
		}

		public void setVersion(Integer version) {
			_version = version;
		}

		private String _name;
		private State _state;
		private Integer _groupId;
		private String _uuid;
		private Integer _version;
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
		File file = getCurrentValue();
		if (file != null && State.UPLOADED.equals(file.getState())) {
			try {
				JSONObject jsonObject = new JSONObject(name);

				file.setUUID(jsonObject.getString("uuid"));
				file.setVersion(jsonObject.getInt("version"));
				file.setGroupId(jsonObject.getInt("groupId"));
				return file;
			}
			catch (JSONException e) {
			}

		}
		return new File();
	}

	@Override
	protected String convertToData(File file) {
		if (State.UPLOADED.equals(getCurrentValue().getState())) {
			return "{groupId:" + file.getGroupId() + ",uuid:" + file.getUUID() + "," +
					"version:" + file.getVersion() + "}";
		}
		return null;
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
