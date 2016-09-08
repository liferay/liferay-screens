package com.liferay.mobile.screens.listbookmark;

import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class BookmarkListInteractor extends BaseListInteractor<BaseListInteractorListener<Bookmark>, BookmarkEvent> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
		long folderId = (long) args[0];

		return new BookmarksEntryService(getSession()).getEntries(groupId, folderId, query.getStartRow(),
			query.getEndRow(), query.getComparatorJSONWrapper());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {
		long folderId = (long) args[0];
		return new BookmarksEntryService(getSession()).getEntriesCount(groupId, folderId);
	}

	@Override
	protected BookmarkEvent createEntity(Map<String, Object> stringObjectMap) {
		Bookmark bookmark = new Bookmark(stringObjectMap);
		return new BookmarkEvent(bookmark);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		return String.valueOf(args[0]);
	}
}