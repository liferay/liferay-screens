package com.liferay.mobile.screens.listbookmark;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.SessionContext;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class BookmarkListInteractorImpl extends BaseListInteractor<Bookmark, BaseListInteractorListener<Bookmark>> {

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {
		Session session = SessionContext.createSessionFromCurrentSession();

		long folderId = (long) args[0];

		return new BookmarksEntryService(session).getEntries(groupId, folderId, query.getStartRow(), query.getEndRow(),
			query.getObjC());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		long folderId = (long) args[0];

		Session session = SessionContext.createSessionFromCurrentSession();
		return new BookmarksEntryService(session).getEntriesCount(groupId, folderId);
	}

	@Override
	protected Bookmark createEntity(Map<String, Object> stringObjectMap) {
		return null;
	}

	@Override
	protected BaseListEvent<Bookmark> createEventFromArgs(Object... args) throws Exception {
		return null;
	}
}