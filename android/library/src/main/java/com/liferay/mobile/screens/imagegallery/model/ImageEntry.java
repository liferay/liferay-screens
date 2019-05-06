package com.liferay.mobile.screens.imagegallery.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.asset.AssetEntry;
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

    private Bitmap image;
    private String imageUrl;
    private String thumbnailUrl;
    private String mimeType;
    private String description;
    private Long createDate;
    private Long creatorUserId;
    private Long fileEntryId;
    private Long repositoryId;
    private Long folderId;

    public ImageEntry(long fileEntryId) {
        this.fileEntryId = fileEntryId;
    }

    public ImageEntry(Map<String, Object> values) {
        super(values);
        parseServerValues();
    }

    public ImageEntry() {
        super();
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
        return values.get(field);
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

    public long getCreatorUserId() {
        return creatorUserId;
    }

    public long getFileEntryId() {
        return fileEntryId;
    }

    public long getRepositoryId() {
        return repositoryId;
    }

    public long getFolderId() {
        return folderId;
    }

    private void parseServerValues() {
        imageUrl = createImageUrl();
        thumbnailUrl = createThumbnailUrl();
        mimeType = (String) values.get("mimeType");
        description = (String) values.get("description");
        createDate = JSONUtil.castToLong(values.get("createDate"));
        creatorUserId = JSONUtil.castToLong(values.get("userId"));
        fileEntryId = JSONUtil.castToLong(values.get("fileEntryId"));
        folderId = JSONUtil.castToLong(values.get("folderId"));
        repositoryId = JSONUtil.castToLong(values.get("repositoryId"));
    }

    private String createThumbnailUrl() {
        return createImageUrl() + "?version=" + values.get("version") + "&imageThumbnail=1";
    }

    private String createImageUrl() {
        return LiferayServerContext.getServer()
            + "/documents/"
            + values.get("groupId")
            + "/"
            + values.get("folderId")
            + "/"
            + encodeUrlString((String) values.get("title"))
            + "/"
            + values.get("uuid");
    }

    private String encodeUrlString(String urlToEncode) {
        try {
            return URLEncoder.encode(urlToEncode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LiferayLogger.e("Error encoding string: " + e.getMessage());
            return "";
        }
    }
}
