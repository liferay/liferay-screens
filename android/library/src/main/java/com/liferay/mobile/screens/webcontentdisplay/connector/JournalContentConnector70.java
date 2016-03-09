package com.liferay.mobile.screens.webcontentdisplay.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.journalarticle.JournalArticleService;

/**
 * @author Javier Gamarra
 */
public class JournalContentConnector70 implements JournalContentConnector {
	public JournalContentConnector70(Session session) {
		_journalArticleService = new JournalArticleService(session);
	}

	@Override
	public void getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper) throws Exception {
		_journalArticleService.getArticleContent(groupId, articleId, locale, jsonObjectWrapper);
	}

	private final JournalArticleService _journalArticleService;
}
