package com.liferay.mobile.screens.gallery.interactor;

import android.support.annotation.NonNull;
import android.util.Pair;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.dlapp.DLAppService;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.gallery.model.ImageEntry;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Víctor Galán Grande
 */
public class GalleryInteractorImpl extends BaseListInteractor<ImageEntry, GalleryInteractorListener> implements GalleryInteractor {

  private long _groupId;
  private long _folderId;

  public GalleryInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
    super(targetScreenletId, offlinePolicy);
  }

  @Override public void loadRows(int startRow, int endRow, long groupId, long folderId)
      throws Exception {

    _groupId = groupId;
    _folderId = folderId;

    super.loadRows(startRow, endRow, Locale.getDefault());
  }

  @NonNull @Override protected ImageEntry getElement(TableCache tableCache) throws JSONException {
    return null;
  }

  @Override protected String getContent(ImageEntry object) {
    return null;
  }

  @Override protected BaseListCallback<ImageEntry> getCallback(Pair<Integer, Integer> rowsRange,
      Locale locale) {
    return new GalleryCallback(getTargetScreenletId(), rowsRange, locale);
  }

  @Override
  protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale)
      throws Exception {
    new DLAppService(session).getFileEntries(_groupId, _folderId, getMimeTypes(), startRow, endRow, null);
    //new DLAppService(session).getGroupFileEntries(20147, 0, 0, new JSONArray().put("image/png"), 0, startRow, endRow, null);
  }

  @Override protected void getPageRowCountRequest(Session session) throws Exception {
    new DLAppService(session).getFileEntriesCount(_groupId, _folderId, getMimeTypes());
    //new DLAppService(session).getGroupFileEntriesCount(20147, 0, 0, new JSONArray().put("image/png"), 0);
  }

  @Override protected boolean cached(Object... args) throws Exception {
    return false;
  }

  @Override protected void storeToCache(BaseListEvent event, Object... args) {

  }

  private JSONArray getMimeTypes() {
    return new JSONArray().put("image/png");
  }
}
