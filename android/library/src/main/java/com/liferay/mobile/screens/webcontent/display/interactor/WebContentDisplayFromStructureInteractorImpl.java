package com.liferay.mobile.screens.webcontent.display.interactor;

import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;
import java.util.Locale;
import org.json.JSONObject;

/**
 * @author Javier Gamarra
 */
public class WebContentDisplayFromStructureInteractorImpl extends WebContentDisplayBaseInteractorImpl {

	@Override
	public WebContentDisplayEvent execute(Object... args) throws Exception {

		String articleId = (String) args[0];
		Long structureId = (Long) args[1];

		validate(structureId, groupId, articleId, locale);

		JournalContentConnector journalArticleConnector =
			ServiceProvider.getInstance().getJournalContentConnector(getSession());
		JSONObject article = journalArticleConnector.getArticle(groupId, articleId);

		DDMStructureConnector structureConnector = ServiceProvider.getInstance().getDDMStructureConnector(getSession());
		JSONObject ddmStructure = structureConnector.getStructure(structureId);
		article.put("DDMStructure", ddmStructure);

		WebContent webContent = new WebContent(JSONUtil.toMap(article), locale);
		return new WebContentDisplayEvent(webContent);
	}

	@Override
	public void onFailure(Exception e) {
		getListener().error(e, WebContentDisplayScreenlet.WEB_CONTENT_WITH_STRUCTURE);
	}

	@Override
	public void onSuccess(WebContentDisplayEvent event) throws Exception {
		getListener().onWebContentReceived(event.getWebContent());
	}

	@Override
	protected String getIdFromArgs(Object... args) throws Exception {
		String articleId = (String) args[0];
		Long structureId = (Long) args[1];
		return articleId + (structureId == null ? "-" : structureId);
	}

	private void validate(Long structureId, long groupId, String articleId, Locale locale) {
		super.validate(locale);

		if (groupId == 0) {
			throw new IllegalArgumentException("GroupId cannot be null");
		} else if (structureId == null || structureId == 0L) {
			throw new IllegalArgumentException("StructureId cannot be null");
		} else if (articleId == null || articleId.isEmpty()) {
			throw new IllegalArgumentException("ArticleId cannot be null");
		}
	}
}
