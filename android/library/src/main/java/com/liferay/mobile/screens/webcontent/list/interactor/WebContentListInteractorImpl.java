package com.liferay.mobile.screens.webcontent.list.interactor;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListCallback;
import com.liferay.mobile.screens.base.list.interactor.BaseListEvent;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Map;

import static com.liferay.mobile.screens.cache.DefaultCachedType.WEB_CONTENT_LIST;
import static com.liferay.mobile.screens.cache.DefaultCachedType.WEB_CONTENT_LIST_COUNT;

/**
 * @author Javier Gamarra
 */
public class WebContentListInteractorImpl extends BaseListInteractor<WebContent, WebContentListInteractorListener>
	implements WebContentListInteractor {

	public WebContentListInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void load(long groupId, long folderId, int startRow, int endRow, Locale locale, String obcClassName)
		throws Exception {

		_groupId = groupId;
		_folderId = folderId;

		processWithCache(startRow, endRow, locale, obcClassName);
	}

	@Override
	protected void getPageRowsRequest(Session session, int startRow, int endRow, Locale locale, JSONObjectWrapper obc)
		throws Exception {

		JournalContentConnector journalContentConnector = ServiceProvider.getInstance().getJournalContentConnector(session);
		journalContentConnector.getJournalArticles(_groupId, _folderId, startRow, endRow, obc);
	}

	@Override
	protected void getPageRowCountRequest(Session session) throws Exception {

		JournalContentConnector journalContentConnector = ServiceProvider.getInstance().getJournalContentConnector(session);
		journalContentConnector.getJournalArticlesCount(_groupId, _folderId);
	}

	@Override
	protected BaseListCallback<WebContent> getCallback(Pair<Integer, Integer> rowsRange, final Locale locale) {
		return new BaseListCallback<WebContent>(getTargetScreenletId(), rowsRange, locale) {
			@Override
			public WebContent createEntity(Map<String, Object> stringObjectMap) {
				return new WebContent(stringObjectMap, locale);
			}
		};
	}

	@Override
	protected boolean cached(Object... args) throws Exception {

		final int startRow = (int) args[0];
		final int endRow = (int) args[1];
		final Locale locale = (Locale) args[2];

		return recoverRows(String.valueOf(_folderId), WEB_CONTENT_LIST, WEB_CONTENT_LIST_COUNT, _groupId, null, locale, startRow, endRow);
	}

	@Override
	protected void storeToCache(BaseListEvent event, Object... args) {

		storeRows(String.valueOf(_folderId), WEB_CONTENT_LIST, WEB_CONTENT_LIST_COUNT, _groupId, null, event);
	}

	@NonNull
	@Override
	protected WebContent getElement(TableCache tableCache) throws JSONException {
		return new WebContent(JSONUtil.toMap(new JSONObject(tableCache.getContent())),
			new Locale(tableCache.getLocale()));
	}

	@Override
	protected String getContent(WebContent object) {
		return new JSONObject(object.getValues()).toString();
	}

	private long _groupId;
	private long _folderId;
}
