package com.liferay.mobile.screens.webcontent.list.interactor;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.webcontent.WebContent;

import org.json.JSONException;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class WebContentListInteractorImpl extends BaseListInteractor<WebContent, WebContentListInteractorListener> {

	public WebContentListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@NonNull
	@Override
	protected WebContent getElement(TableCache tableCache) throws JSONException {
		return null;
	}

	@Override
	protected String getContent(WebContent object) {
		return null;
	}

	@Override
	protected BaseListCallback<WebContent> getCallback(Pair<Integer, Integer> rowsRange, Locale locale) {
		return null;
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale) throws Exception {

	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {

	}

	@Override
	protected boolean cached(Object... args) throws Exception {
		return false;
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

	}
}
