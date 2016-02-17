package com.liferay.mobile.screens.webcontentdisplay.operation;

/**
 * @author Javier Gamarra
 */
public interface ScreensJournalContentOperation {
	void getJournalArticleContent(long classPK, String locale) throws Exception;

	void getJournalArticleContent(long classPK, long templateId, String locale) throws Exception;

	void getJournalArticleContent(long groupId, String articleId, Long templateId, String locale) throws Exception;
}
