package com.liferay.mobile.screens.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public class CommentEntry implements Parcelable {
	public CommentEntry() {

	}

	public static final ClassLoaderCreator<CommentEntry> CREATOR =
		new ClassLoaderCreator<CommentEntry>() {

			@Override
			public CommentEntry createFromParcel(Parcel source, ClassLoader loader) {
				return new CommentEntry(source, loader);
			}

			public CommentEntry createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public CommentEntry[] newArray(int size) {
				return new CommentEntry[size];
			}
		};

	protected CommentEntry(Parcel in, ClassLoader loader) {
		_values = new HashMap<>();

		in.readMap(_values, loader);
	}

	public CommentEntry(Map<String, Object> values) {
		_values = values;
	}

	public String getBody() {
		return (String) _values.get("body");
	}

	public long getUserId() {
		return (long) _values.get("userId");
	}

	public String getUserName() {
		return (String) _values.get("userName");
	}

	public Date getCreateDate() {
		return new Date(((long) _values.get("createDate")) * 1000);
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(_values);
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	protected Map<String, Object> _values;

}
