package com.liferay.mobile.screens.webcontent.display.connector;

/**
 * @author Javier Gamarra
 */
public interface ScreensJournalContentConnector {

    String getJournalArticleContent(long groupId, String articleId, long templateId, String locale) throws Exception;
}
