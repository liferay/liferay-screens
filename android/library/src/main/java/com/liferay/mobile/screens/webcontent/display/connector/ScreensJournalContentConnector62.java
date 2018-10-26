package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.service.v62.ScreensjournalarticleService;

/**
 * @author Javier Gamarra
 */
public class ScreensJournalContentConnector62 implements ScreensJournalContentConnector {

    private final ScreensjournalarticleService screensJournalContent;

    public ScreensJournalContentConnector62(Session session) {
        screensJournalContent = new ScreensjournalarticleService(session);
    }

    @Override
    public String getJournalArticleContent(long groupId, String articleId, long templateId, String locale)
        throws Exception {
        return screensJournalContent.getJournalArticleContent(groupId, articleId, templateId, locale);
    }
}
