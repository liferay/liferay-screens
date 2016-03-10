package com.liferay.mobile.screens.bookmark.interactor;

import android.webkit.URLUtil;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.base.interactor.JSONObjectCallback;
import com.liferay.mobile.screens.base.interactor.JSONObjectEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkInteractorImpl
	extends BaseRemoteInteractor<AddBookmarkListener>
	implements AddBookmarkInteractor {

	public AddBookmarkInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void addBookmark(String url, String title, long folderId) throws Exception {
		if (url == null || url.isEmpty() || !URLUtil.isValidUrl(url)) {
			throw new IllegalArgumentException("Invalid url");
		}

		if (folderId == 0) {
			throw new IllegalArgumentException("folderId not set");
		}

		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new JSONObjectCallback(getTargetScreenletId()));
		if (LiferayServerContext.isLiferay7()) {
			new BookmarksEntryService(session)
				.addEntry(LiferayServerContext.getGroupId(), folderId, title, url, "", null);
		}
		else {
			new com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService(session)
				.addEntry(LiferayServerContext.getGroupId(), folderId, title, url, "", null);
		}
	}

	public void onEvent(JSONObjectEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onAddBookmarkFailure(event.getException());
		}
		else {
			getListener().onAddBookmarkSuccess();
		}
	}
}

