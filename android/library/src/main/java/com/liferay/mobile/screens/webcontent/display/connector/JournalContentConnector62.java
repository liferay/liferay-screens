package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.journalarticle.JournalArticleService;

/**
 * @author Javier Gamarra
 */
public class JournalContentConnector62 implements JournalContentConnector {

	public JournalContentConnector62(Session session) {
		_journalArticleService = new JournalArticleService(session);
	}

	@Override
	public void getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper) throws Exception {
		_journalArticleService.getArticleContent(groupId, articleId, locale, jsonObjectWrapper);
	}

	@Override
	public void getArticle(Long groupId, String articleId) throws Exception {
		_journalArticleService.getArticle(groupId, articleId);
	}

	private final JournalArticleService _journalArticleService;
}
