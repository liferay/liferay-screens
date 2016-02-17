package com.liferay.mobile.screens.webcontentdisplay.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentOperation62 implements ScreensJournalContentOperation {
	public ScreensJournalContentOperation62(Session session) {
		_screensJournalContent = new ScreensjournalarticleService(session);
	}

	@Override
	public void getJournalArticleContent(long classPK, String locale) throws Exception {
		_screensJournalContent.getJournalArticleContent(classPK, locale);
	}

	@Override
	public void getJournalArticleContent(long classPK, long templateId, String locale) throws Exception {
		_screensJournalContent.getJournalArticleContent(classPK, templateId, locale);
	}

	@Override
	public void getJournalArticleContent(long groupId, String articleId, Long templateId, String locale) throws Exception {

	}

	private final ScreensjournalarticleService _screensJournalContent;
}
