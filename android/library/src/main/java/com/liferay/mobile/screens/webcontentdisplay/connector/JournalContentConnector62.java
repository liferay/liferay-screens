package com.liferay.mobile.screens.webcontentdisplay.connector;

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
	public String getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper) throws Exception {
		return _journalArticleService.getArticleContent(groupId, articleId, locale, jsonObjectWrapper);
	}

	private final JournalArticleService _journalArticleService;
}
