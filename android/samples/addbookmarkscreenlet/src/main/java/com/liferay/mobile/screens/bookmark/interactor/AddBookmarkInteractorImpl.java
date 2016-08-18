package com.liferay.mobile.screens.bookmark.interactor;

import android.support.annotation.NonNull;
import android.webkit.URLUtil;
import com.liferay.mobile.android.v7.bookmarksentry.BookmarksEntryService;
import com.liferay.mobile.screens.base.thread.BaseRemoteInteractorNew;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.context.LiferayServerContext;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkInteractorImpl extends BaseRemoteInteractorNew<AddBookmarkListener, BasicThreadEvent> {

	@Override
	public BasicThreadEvent execute(Object[] args) throws Exception {
		String url = (String) args[0];
		String title = (String) args[1];
		long folderId = (long) args[2];

		validate(url, folderId);

		JSONObject jsonObject = getJSONObject(url, title, folderId);
		return new BasicThreadEvent(jsonObject);
	}

	@Override
	public void onSuccess(BasicThreadEvent event) throws Exception {
		getListener().onAddBookmarkSuccess();
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onAddBookmarkFailure(e);
	}

	private void validate(String url, long folderId) {
		if (url == null || url.isEmpty() || !URLUtil.isValidUrl(url)) {
			throw new IllegalArgumentException("Invalid url");
		} else if (folderId == 0) {
			throw new IllegalArgumentException("folderId not set");
		}
	}

	@NonNull
	private JSONObject getJSONObject(String url, String title, long folderId) throws Exception {
		if (LiferayServerContext.isLiferay7()) {
			return new BookmarksEntryService(getSession()).addEntry(LiferayServerContext.getGroupId(), folderId, title,
				url, "", null);
		} else {
			return new com.liferay.mobile.android.v62.bookmarksentry.BookmarksEntryService(getSession()).addEntry(
				LiferayServerContext.getGroupId(), folderId, title, url, "", null);
		}
	}
}

