package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public interface JournalContentConnector {

    String getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper)
        throws Exception;

    JSONObject getArticle(Long groupId, String articleId) throws Exception;

    JSONArray getJournalArticles(long groupId, long folderId, int start, int end, JSONObjectWrapper obc)
        throws Exception;

    Integer getJournalArticlesCount(long groupId, long folderId) throws Exception;
}
