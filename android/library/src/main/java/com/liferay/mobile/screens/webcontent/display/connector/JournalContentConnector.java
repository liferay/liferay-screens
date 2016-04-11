package com.liferay.mobile.screens.webcontent.display.connector;

import com.liferay.mobile.android.service.JSONObjectWrapper;

/**
 * @author Javier Gamarra
 */
public interface JournalContentConnector {

	void getArticleContent(long groupId, String articleId, String locale, JSONObjectWrapper jsonObjectWrapper) throws Exception;

	void getArticle(Long groupId, String articleId) throws Exception;
}
