package com.liferay.mobile.screens.listbookmark;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.cache.CachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class BookmarkListInteractorImpl
	extends BaseListInteractor<Bookmark, BaseListInteractorListener<Bookmark>> {

	private enum BOOKMARK_LIST implements CachedType {
		BOOKMARK, BOOKMARK_COUNT
	}

	public BookmarkListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void loadRows(int startRow, int endRow, Locale locale, long groupId, long folderId,
			String obcClassName) throws Exception {

		_groupId = groupId;
		_folderId = folderId;

		processWithCache(startRow, endRow, locale, obcClassName);
	}

	@Override
	protected BaseListCallback<Bookmark> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return new BaseListCallback<Bookmark>(getTargetScreenletId(), rowsRange, locale) {
			@Override
			public Bookmark createEntity(Map<String, Object> stringObjectMap) {
				return new Bookmark(stringObjectMap);
			}
		};
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale,
		JSONObjectWrapper obc) throws Exception {

		new BookmarksEntryService(session).getEntries(_groupId, _folderId, startRow, endRow, obc);
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		new BookmarksEntryService(session).getEntriesCount(_groupId, _folderId);
	}

	@NonNull
	@Override
	protected Bookmark getElement(TableCache tableCache) throws JSONException {
		return new Bookmark(JSONUtil.toMap(new JSONObject(tableCache.getContent())));
	}

	@Override
	protected String getContent(Bookmark object) {
		return new JSONObject(object.getValues()).toString();
	}

	@Override
	protected boolean cached(Object... args) throws Exception {
		final int startRow = (int) args[0];
		final int endRow = (int) args[1];
		final Locale locale = (Locale) args[2];

		return recoverRows(String.valueOf(_folderId), BOOKMARK_LIST.BOOKMARK,
			BOOKMARK_LIST.BOOKMARK_COUNT, _groupId, null, locale, startRow, endRow);
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {
		storeRows(String.valueOf(_folderId), BOOKMARK_LIST.BOOKMARK,
			BOOKMARK_LIST.BOOKMARK_COUNT, _groupId, null, event);
	}

	private long _groupId;
	private long _folderId;
}
