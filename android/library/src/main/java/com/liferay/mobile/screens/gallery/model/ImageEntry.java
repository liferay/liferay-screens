package com.liferay.mobile.screens.gallery.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.asset.list.AssetEntry;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

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

	private ImageEntry(Parcel in, ClassLoader loader) {
		super(in, loader);
		imageUrl = in.readString();
		thumbnailUrl = in.readString();
		mimeType = in.readString();
		description = in.readString();
		createDate = (Long) in.readValue(Long.class.getClassLoader());
		creatorUserId = (Long) in.readValue(Long.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(imageUrl);
		dest.writeString(thumbnailUrl);
		dest.writeString(mimeType);
		dest.writeString(description);
		dest.writeValue(createDate);
		dest.writeValue(creatorUserId);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public Object getServerAttribute(String field) {
		return _values.get(field);
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public Bitmap getImage() {
		return image;
	}

	public void setImage(Bitmap image) {
		this.image = image;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String description() {
		return description;
	}

	public long createDate() {
		return createDate;
	}

	public long getCreatorUserId() {
		return creatorUserId;
	}

	public long getFileEntryId() {
		return fileEntryId;
	}

	private void parseServerValues() {
		imageUrl = createImageUrl();
		thumbnailUrl = createThumbnailUrl();
		mimeType = (String) _values.get("mimeType");
		description = (String) _values.get("description");
		createDate = JSONUtil.castToLong(_values.get("createDate"));
		creatorUserId = JSONUtil.castToLong(_values.get("userId"));
		fileEntryId = JSONUtil.castToLong(_values.get("fileEntryId"));
	}

	private String createThumbnailUrl() {
		return createImageUrl() + "?version=" + _values.get("version") + "&imageThumbnail=1";
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

	private Bitmap image;
	private String imageUrl;
	private String thumbnailUrl;
	private String mimeType;
	private String description;
	private Long createDate;
	private Long creatorUserId;
	private Long fileEntryId;
}
