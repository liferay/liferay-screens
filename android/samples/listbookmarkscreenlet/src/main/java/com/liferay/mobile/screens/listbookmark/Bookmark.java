package com.liferay.mobile.screens.listbookmark;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class Bookmark implements Parcelable {

	public static final Creator<Bookmark> CREATOR = new Creator<Bookmark>() {
		@Override
		public Bookmark createFromParcel(Parcel in) {
			return new Bookmark(in);
		}

		@Override
		public Bookmark[] newArray(int size) {
			return new Bookmark[size];
		}
	};

	protected Bookmark(Parcel in) {
		_url = in.readString();
	}

	public Bookmark(Map<String, Object> stringObjectMap) {
		_url = (String) stringObjectMap.get("url");
		_values = stringObjectMap;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(_url);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public String getUrl() {
		return _url;
	}

	public Map getValues() {
		return _values;
	}

	public void setValues(Map values) {
		_values = values;
	}

	private String _url;
	private Map _values;
}
