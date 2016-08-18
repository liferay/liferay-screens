package com.liferay.mobile.screens.webcontent.list.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class WebContentListInteractorImpl extends BaseListInteractor<WebContent, WebContentListInteractorListener> {

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		long folderId = (long) args[0];

		return String.valueOf(folderId);
	}

	@Override
	protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

		long _folderId = (long) args[0];

		Session session = getSession();

		JournalContentConnector journalContentConnector =
			ServiceProvider.getInstance().getJournalContentConnector(session);
		return journalContentConnector.getJournalArticles(groupId, _folderId, query.getStartRow(), query.getEndRow(),
			query.getObjC());
	}

	@Override
	protected Integer getPageRowCountRequest(Object... args) throws Exception {

		long _folderId = (long) args[0];

		JournalContentConnector journalContentConnector =
			ServiceProvider.getInstance().getJournalContentConnector(getSession());
		return journalContentConnector.getJournalArticlesCount(groupId, _folderId);
	}

	@Override
	protected WebContent createEntity(Map<String, Object> stringObjectMap) {
		return new WebContent(stringObjectMap, locale);
	}
}
