package com.liferay.mobile.screens.listbookmark;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;

import org.json.JSONException;

import java.util.Locale;
import java.util.Map;

/**
 * @author Javier Gamarra
 */
public class BookmarkListInteractorImpl
	extends BaseListInteractor<Bookmark, BaseListInteractorListener<Bookmark>> {

	public BookmarkListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void loadRows(int startRow, int endRow, Locale locale, long groupId, long folderId) throws Exception {

		_groupdId = groupId;
		_folderId = folderId;

		super.loadRows(startRow, endRow, locale);
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
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {
		new BookmarksEntryService(session).getEntries(_groupdId, _folderId, startRow, endRow);
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {
		new BookmarksEntryService(session).getEntriesCount(_groupdId, _folderId);
	}

	@NonNull
	@Override
	protected Bookmark getElement(TableCache tableCache) throws JSONException {
		return null;
	}

	@Override
	protected String getContent(Bookmark object) {
		return null;
	}

	@Override
	protected boolean cached(Object... args) throws Exception {
		return false;
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

	}

	private long _groupdId;
	private long _folderId;
}
