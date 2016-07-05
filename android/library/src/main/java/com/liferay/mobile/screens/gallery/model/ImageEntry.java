package com.liferay.mobile.screens.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Víctor Galán Grande
 */
public class ImageEntry extends AssetEntry implements Parcelable {

	public static final ClassLoaderCreator<AssetEntry> CREATOR = new ClassLoaderCreator<AssetEntry>() {

			@Override
			public ImageEntry createFromParcel(Parcel source, ClassLoader loader) {
				return new ImageEntry(source, loader);
			}

			public ImageEntry createFromParcel(Parcel in) {
				throw new AssertionError();
			}

			public ImageEntry[] newArray(int size) {
				return new ImageEntry[size];
			}
		};

	public ImageEntry(Map<String, Object> values) {
		super(values);
		parseServerValues();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(_imageUrl);
		dest.writeString(_thumbnailUrl);
		dest.writeString(_mimeType);
		dest.writeString(_description);
		dest.writeValue(_createDate);
		dest.writeValue(_creatorUserId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Object getServerAttribute(String field) {
		return _values.get(field);
	}

	public String getImageUrl() {
		return _imageUrl;
	}

	public String getThumbnailUrl() {
		return _thumbnailUrl;
	}

	public String getMimeType() {
		return _mimeType;
	}

	public String description() {
		return _description;
	}

	public long createDate() {
		return _createDate;
	}

	public long getCreatorUserId() {
		return _creatorUserId;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public boolean thumbnailNotAlreadyGenerated() {
		return isLessThan60secondsOld();
	}

	private ImageEntry(Parcel in, ClassLoader loader) {
		super(in, loader);
		_imageUrl = in.readString();
		_thumbnailUrl = in.readString();
		_mimeType = in.readString();
		_description = in.readString();
		_createDate = (Long) in.readValue(Long.class.getClassLoader());
		_creatorUserId = (Long) in.readValue(Long.class.getClassLoader());
	}

	private void parseServerValues() {
		_imageUrl = createImageUrl();
		_thumbnailUrl = createThumbnailUrl();
		_mimeType = (String) _values.get("mimeType");
		_description = (String) _values.get("description");
		_createDate = JSONUtil.castToLong(_values.get("createDate"));
		_creatorUserId = JSONUtil.castToLong(_values.get("userId"));
		_fileEntryId = JSONUtil.castToLong(_values.get("fileEntryId"));
	}

	private String createThumbnailUrl() {
		return createImageUrl()
			+ "?version="
			+ _values.get("version")
			+ "&imageThumbnail=1";
	}

	private String createImageUrl() {
		return LiferayServerContext.getServer()
			+ "/documents/"
			+ _values.get("groupId")
			+ "/"
			+ _values.get("folderId")
			+ "/"
			+ encodeUrlString((String) _values.get("title"))
			+ "/"
			+ _values.get("uuid");
	}

	private String encodeUrlString(String urlToEncode) {
		try {
			return URLEncoder.encode(urlToEncode, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LiferayLogger.e("Error encoding string: " + e.getMessage());
			return "";
		}
	}

	private boolean isLessThan60secondsOld() {
		long creationMinutes = TimeUnit.MILLISECONDS.toSeconds(_createDate);
		long actualMinutes = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());

		return actualMinutes - creationMinutes <= 10;
	}

	private String _imageUrl;
	private String _thumbnailUrl;
	private String _mimeType;
	private String _description;
	private Long _createDate;
	private Long _creatorUserId;
	private Long _fileEntryId;
}
