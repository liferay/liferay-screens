package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.journalarticle.JournalArticleService;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class JournalContentConnector62 implements JournalContentConnector {

	public JournalContentConnector62(Session session) {
		_journalArticleService = new JournalArticleService(session);
	}

	@Override
	public String getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper) throws Exception {
		return _journalArticleService.getArticleContent(groupId, articleId, locale, jsonObjectWrapper);
	}

	@Override
	public JSONObject getArticle(Long groupId, String articleId) throws Exception {
		return _journalArticleService.getArticle(groupId, articleId);
	}

	@Override
	public JSONArray getJournalArticles(long groupId, long folderId, int start, int end, JSONObjectWrapper obc) throws Exception {
		return _journalArticleService.getArticles(groupId, folderId, start, end, obc);
	}

	@Override
	public Integer getJournalArticlesCount(long groupId, long folderId) throws Exception {
		return _journalArticleService.getArticlesCount(groupId, folderId);
	}

	private final JournalArticleService _journalArticleService;
}
