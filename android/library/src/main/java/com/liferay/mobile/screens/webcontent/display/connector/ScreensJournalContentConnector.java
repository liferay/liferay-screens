package com.liferay.mobile.screens.webcontent.display.connector;

/**
 * @author Javier Gamarra
 */
public interface ScreensJournalContentConnector {
	void getJournalArticleContent(long classPK, String locale) throws Exception;

	void getJournalArticleContent(long classPK, long templateId, String locale) throws Exception;

	void getJournalArticleContent(long groupId, String articleId, long templateId, String locale) throws Exception;

}
