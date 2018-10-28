package com.liferay.mobile.screens.webcontent.list.interactor;

import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayEvent;
import java.util.Map;
import org.json.JSONArray;

/**
 * @author Javier Gamarra
 */
public class WebContentListInteractor
    extends BaseListInteractor<BaseListInteractorListener<WebContent>, WebContentDisplayEvent> {

    @Override
    protected String getIdFromArgs(Object... args) {
        long folderId = (long) args[0];

        return String.valueOf(folderId);
    }

    @Override
    protected JSONArray getPageRowsRequest(Query query, Object... args) throws Exception {

        long folderId = (long) args[0];

        JournalContentConnector journalContentConnector =
            ServiceProvider.getInstance().getJournalContentConnector(getSession());
        return journalContentConnector.getJournalArticles(groupId, folderId, query.getStartRow(), query.getEndRow(),
            query.getComparatorJSONWrapper());
    }

    @Override
    protected Integer getPageRowCountRequest(Object... args) throws Exception {

        long folderId = (long) args[0];

        JournalContentConnector journalContentConnector =
            ServiceProvider.getInstance().getJournalContentConnector(getSession());
        return journalContentConnector.getJournalArticlesCount(groupId, folderId);
    }

    @Override
    protected WebContentDisplayEvent createEntity(Map<String, Object> stringObjectMap) {
        return new WebContentDisplayEvent(new WebContent(stringObjectMap, locale));
    }
}
