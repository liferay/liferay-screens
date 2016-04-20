package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentConnector70 implements ScreensJournalContentConnector {

	public ScreensJournalContentConnector70(Session session) {
		_screensjournalarticleService = new ScreensjournalarticleService(session);
	}

	@Override
	public void getJournalArticleContent(long classPK, String locale) throws Exception {
		_screensjournalarticleService.getJournalArticleContent(classPK, locale);
	}

	@Override
	public void getJournalArticleContent(long classPK, long templateId, String locale) throws Exception {
		_screensjournalarticleService.getJournalArticleContent(classPK, templateId, locale);
	}

	@Override
	public void getJournalArticleContent(long groupId, String articleId, long templateId, String locale) throws Exception {
		_screensjournalarticleService.getJournalArticleContent(groupId, articleId, templateId, locale);
	}

	private final ScreensjournalarticleService _screensjournalarticleService;
}
