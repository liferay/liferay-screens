package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v70.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentConnector70 implements ScreensJournalContentConnector {

    private final ScreensjournalarticleService screensjournalarticleService;

    public ScreensJournalContentConnector70(Session session) {
        screensjournalarticleService = new ScreensjournalarticleService(session);
    }

    @Override
    public String getJournalArticleContent(long groupId, String articleId, long templateId, String locale)
        throws Exception {
        return screensjournalarticleService.getJournalArticleContent(groupId, articleId, templateId, locale);
    }
}
