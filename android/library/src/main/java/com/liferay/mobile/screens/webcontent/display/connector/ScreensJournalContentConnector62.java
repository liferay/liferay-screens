package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentConnector62 implements ScreensJournalContentConnector {

	public ScreensJournalContentConnector62(Session session) {
		_screensJournalContent = new ScreensjournalarticleService(session);
	}

	@Override
	public String getJournalArticleContent(long classPK, String locale) throws Exception {
		return _screensJournalContent.getJournalArticleContent(classPK, locale);
	}

	@Override
	public String getJournalArticleContent(long classPK, long templateId, String locale) throws Exception {
		return _screensJournalContent.getJournalArticleContent(classPK, templateId, locale);
	}

	@Override
	public String getJournalArticleContent(long groupId, String articleId, long templateId, String locale) throws Exception {
		return _screensJournalContent.getJournalArticleContent(groupId, articleId, templateId, locale);
	}

	private final ScreensjournalarticleService _screensJournalContent;
}
