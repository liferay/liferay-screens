package com.liferay.mobile.screens.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateUtils;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
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

	public CommentEntry(Parcel in, ClassLoader loader) {
		_values = new HashMap<>();

		in.readMap(_values, loader);
	}

	public CommentEntry(Map<String, Object> values) {
		_values = values;
	}

	public CommentEntry(long commentId, String body) {
		HashMap<String, Object> values = new HashMap<>();
		values.put("commentId", String.valueOf(commentId));
		values.put("body", body);
		values.put("userId", String.valueOf(SessionContext.getUserId()));
		User currentUser = SessionContext.getCurrentUser();
		String userName = currentUser.getFirstName() + " " + currentUser.getLastName();
		values.put("userName", userName);
		values.put("createDate", System.currentTimeMillis());
		values.put("modifiedDate", System.currentTimeMillis());
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
