package com.liferay.mobile.screens.gallery.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.squareup.picasso.Picasso;
import java.util.Map;

/**
 * @author Víctor Galán Grande
 */
public class ImageEntry extends AssetEntry implements Parcelable {

  public ImageEntry(Map<String, Object> values) {
    super(values);
  }

  public String getImageUrl() {
    return (String) _values.get("imageUrl");
  }

  public String getThumbnailUrl() {
    return (String) _values.get("thumbnailUrl");
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
  }

  @Override public int describeContents() {
    return 0;
  }

  public static final ClassLoaderCreator<AssetEntry> CREATOR =
      new ClassLoaderCreator<AssetEntry>() {

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

  public ImageEntry(Parcel in, ClassLoader loader){
    super(in, loader);
  }
}
