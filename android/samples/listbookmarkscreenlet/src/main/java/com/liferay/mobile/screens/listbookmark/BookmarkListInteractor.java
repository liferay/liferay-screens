package com.liferay.mobile.screens.listbookmark;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.bookmark.interactor.v7.BookmarksEntryService;
import com.liferay.mobile.screens.context.LiferayServerContext;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class BookmarkListInteractor extends BaseListInteractor<BaseListInteractorListener<Bookmark>, BookmarkEvent> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
		long folderId = (long) args[0];

		if (args[1] != null) {
			query.setComparator((String) args[1]);
		}

		if (LiferayServerContext.isLiferay7()) {
			return new BookmarksEntryService(getSession()).getEntries(groupId, folderId, query.getStartRow(),
				query.getEndRow(), query.getComparatorJSONWrapper());
		} else {
			return new com.liferay.mobile.screens.bookmark.interactor.v62.BookmarksEntryService(getSession()).getEntries(
				groupId, folderId, query.getStartRow(), query.getEndRow(), query.getComparatorJSONWrapper());
		}
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {
		long folderId = (long) args[0];
		if (LiferayServerContext.isLiferay7()) {
			return new BookmarksEntryService(getSession()).getEntriesCount(groupId, folderId);
		} else {
			return new com.liferay.mobile.screens.bookmark.interactor.v62.BookmarksEntryService(
				getSession()).getEntriesCount(groupId, folderId);
		}
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