package com.liferay.mobile.screens.webcontentdisplay.operation;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v70.screensjournalarticle.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentOperation70 implements ScreensJournalContentOperation {
	public ScreensJournalContentOperation70(Session session) {
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
	public void getJournalArticleContent(long groupId, String articleId, Long templateId, String locale) throws Exception {

	}

	private final ScreensjournalarticleService _screensjournalarticleService;
}
