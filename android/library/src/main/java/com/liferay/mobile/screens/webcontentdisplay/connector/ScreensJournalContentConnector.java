package com.liferay.mobile.screens.webcontentdisplay.connector;

/**
 * @author Javier Gamarra
 */
public interface ScreensJournalContentConnector {
	String getJournalArticleContent(long classPK, String locale) throws Exception;

	String getJournalArticleContent(long classPK, long templateId, String locale) throws Exception;

	String getJournalArticleContent(long groupId, String articleId, long templateId, String locale) throws Exception;
}
