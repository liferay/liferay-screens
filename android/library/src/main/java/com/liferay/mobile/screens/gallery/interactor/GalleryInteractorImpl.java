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
import com.liferay.mobile.screens.util.JSONUtil;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.liferay.mobile.screens.cache.DefaultCachedType.IMAGE_LIST;
import static com.liferay.mobile.screens.cache.DefaultCachedType.IMAGE_LIST_COUNT;

/**
 * @author Víctor Galán Grande
 */
public class GalleryInteractorImpl extends BaseListInteractor<ImageEntry, GalleryInteractorListener>
	implements GalleryInteractor {

	public GalleryInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void loadRows(long groupId, long folderId, int startRow, int endRow, Locale locale) throws Exception {
		_groupId = groupId;
		_folderId = folderId;

		processWithCache(startRow, endRow, locale);
	}

	@NonNull
	@Override
	protected ImageEntry getElement(TableCache tableCache) throws JSONException {
		return new ImageEntry(JSONUtil.toMap(new JSONObject(tableCache.getContent())));
	}

	@Override
	protected String getContent(ImageEntry imageEntry) {
		return new JSONObject(imageEntry.getValues()).toString();
	}

	@Override
	protected BaseListCallback<ImageEntry> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return new GalleryCallback(getTargetScreenletId(), rowsRange, locale);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		new DLAppService(session).getFileEntries(_groupId, _folderId, getMimeTypes(), startRow, endRow, null);
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		new DLAppService(session).getFileEntriesCount(_groupId, _folderId, getMimeTypes());
	}

	@Override
	protected boolean cached(Object... args) throws Exception {

		int startRow = (int) args[0];
		int endRow = (int) args[1];
		Locale locale = (Locale) args[2];

		String id = String.valueOf(_folderId);

		return recoverRows(id, IMAGE_LIST, IMAGE_LIST_COUNT, _groupId, null, locale, startRow, endRow);
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

		String id = String.valueOf(_folderId);

		storeRows(id, IMAGE_LIST, IMAGE_LIST_COUNT, _groupId, null, event);
	}

	private JSONArray getMimeTypes() {
		return new JSONArray()
			.put("image/png")
			.put("image/jpeg")
			.put("image/gif");
	}

	private long _groupId;
	private long _folderId;
}
