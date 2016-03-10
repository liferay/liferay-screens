package com.liferay.mobile.screens.webcontentdisplay.connector;

/**
 * @author Javier Gamarra
 */
public interface ScreensJournalContentConnector {
	void getJournalArticleContent(long classPK, String locale) throws Exception;

	void getJournalArticleContent(long classPK, long templateId, String locale) throws Exception;

	void getJournalArticleContent(long groupId, String articleId, Long templateId, String locale) throws Exception;
}
