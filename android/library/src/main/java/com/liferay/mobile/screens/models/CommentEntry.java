package com.liferay.mobile.screens.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
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
		return Long.valueOf((String) _values.get("userId"));
	}

	public String getUserName() {
		return (String) _values.get("userName");
	}

	public int getChildCount() {
		return (int) _values.get("descendantCommentsCount");
	}

	public long getCreateDate() {
		return (long) _values.get("createDate");
	}

	public String getCreateDateAsTimeSpan() {
		return DateUtils.getRelativeTimeSpanString(
			this.getCreateDate(), System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
	}

	public long getModifiedDate() {
		return (long) _values.get("modifiedDate");
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

	public long getCommentId() {
		return Long.valueOf((String) _values.get("commentId"));
	}

	protected Map<String, Object> _values;

}
