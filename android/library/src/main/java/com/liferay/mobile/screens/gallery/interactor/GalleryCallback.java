package com.liferay.mobile.screens.gallery.interactor;

import android.util.Pair;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import org.json.JSONException;

/**
 * @author Víctor Galán Grande
 */
public class GalleryCallback extends BaseListCallback<ImageEntry> {

  public GalleryCallback(int targetScreenletId, Pair<Integer, Integer> rowsRange, Locale locale) {
    super(targetScreenletId, rowsRange, locale);
  }

  @Override public ImageEntry createEntity(Map<String, Object> stringObjectMap) {

    stringObjectMap.put("imageUrl", createImageUrl(stringObjectMap));
    stringObjectMap.put("thumbnailUrl", createThumbnailUrl(stringObjectMap));

    return new ImageEntry(stringObjectMap);
  }

  private String createThumbnailUrl(Map<String, Object> stringObjectMap) {
    return createImageUrl(stringObjectMap)
        + "?version="
        + stringObjectMap.get("version")
        + "&imageThumbnail=1";
  }

  private String createImageUrl(Map<String, Object> stringObjectMap) {
    try {
      return LiferayServerContext.getServer()
          + "/documents/"
          + stringObjectMap.get("groupId")
          + "/"
          + stringObjectMap.get("folderId")
          + "/"
          + URLEncoder.encode((String) stringObjectMap.get("title"), "UTF-8")
          + "/"
          + stringObjectMap.get("uuid");
    } catch (UnsupportedEncodingException ex) {
      LiferayLogger.e(ex.getMessage());
      return "";
    }
  }
}
