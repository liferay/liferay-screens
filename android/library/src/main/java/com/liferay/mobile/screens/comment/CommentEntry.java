package com.liferay.mobile.screens.comment;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alejandro Hernández
 */
public class CommentEntry implements Parcelable {

	private boolean editable;

	public CommentEntry() {

	}

	public static final ClassLoaderCreator<CommentEntry> CREATOR = new ClassLoaderCreator<CommentEntry>() {

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

	public CommentEntry(Parcel in, ClassLoader loader) {
		values = new HashMap<>();

		in.readMap(values, loader);
	}

	public CommentEntry(Map<String, Object> values) {
		this.values = values;
	}

	public boolean isUpdatable() {
		return (boolean) values.get("updatePermission");
	}

	public boolean isDeletable() {
		return (boolean) values.get("deletePermission");
	}

	public String getBody() {
		return (String) values.get("body");
	}

	public long getUserId() {
		return Long.parseLong((String) values.get("userId"));
	}

	public String getUserName() {
		return (String) values.get("userName");
	}

	public long getCreateDate() {
		return (long) values.get("createDate");
	}

	public String getCreateDateAsTimeSpan() {
		return DateUtils.getRelativeTimeSpanString(this.getCreateDate(), System.currentTimeMillis(),
			DateUtils.SECOND_IN_MILLIS).toString();
	}

	public long getModifiedDate() {
		return (long) values.get("modifiedDate");
	}

	@Override
	public void writeToParcel(Parcel destination, int flags) {
		destination.writeMap(values);
	}

	public Map<String, Object> getValues() {
		return values;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public long getCommentId() {
		return Long.parseLong((String) values.get("commentId"));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}

		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		CommentEntry that = (CommentEntry) o;

		return values != null && getCommentId() == that.getCommentId();
	}

	protected Map<String, Object> values;

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public boolean isEditable() {
		return this.editable;
	}

	public String getClassName() {
		return (String) values.get("className");
	}

	public long getClassPK() {
		return (long) values.get("classPK");
	}
}
