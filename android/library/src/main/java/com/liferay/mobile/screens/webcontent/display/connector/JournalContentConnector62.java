package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.JournalArticleService;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class JournalContentConnector62 implements JournalContentConnector {

    private final JournalArticleService journalArticleService;

    public JournalContentConnector62(Session session) {
        journalArticleService = new JournalArticleService(session);
    }

    @Override
    public String getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper)
        throws Exception {
        return journalArticleService.getArticleContent(groupId, articleId, locale, jsonObjectWrapper);
    }

    @Override
    public JSONObject getArticle(Long groupId, String articleId) throws Exception {
        return journalArticleService.getArticle(groupId, articleId);
    }

    @Override
    public JSONArray getJournalArticles(long groupId, long folderId, int start, int end, JSONObjectWrapper obc)
        throws Exception {
        return journalArticleService.getArticles(groupId, folderId, start, end, obc);
    }

    @Override
    public Integer getJournalArticlesCount(long groupId, long folderId) throws Exception {
        return journalArticleService.getArticlesCount(groupId, folderId);
    }
}
