package com.liferay.mobile.screens.webcontent.display.interactor;

import com.liferay.mobile.android.service.BatchSessionImpl;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.ddl.form.connector.DDMStructureConnector;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.util.JSONUtil;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontent.display.connector.JournalContentConnector;

import org.json.JSONObject;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class WebContentDisplayFromStructureInteractorImpl
	extends WebContentDisplayBaseInteractorImpl
	implements WebContentDisplayFromStructureInteractor {

	public WebContentDisplayFromStructureInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	@Override
	public void load(Long structureId, long groupId, String articleId, Locale locale) throws Exception {
		validate(structureId, groupId, articleId, locale);

		processWithCache(structureId, groupId, articleId, locale);
	}

	public void onEvent(WebContentDisplayEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event, event.getStructureId(), event.getArticleId(), event.getLocale());

		if (!event.isFailed()) {
			getListener().onWebContentReceived(null, event.getWebContent());
		}
	}

	@Override
	protected void online(Object... args) throws Exception {

		Long structureId = (Long) args[0];
		Long groupId = (Long) args[1];
		String articleId = (String) args[2];
		Locale locale = (Locale) args[3];

		Session session = SessionContext.createSessionFromCurrentSession();
		BatchSessionImpl batchSession = new BatchSessionImpl(session);
		batchSession.setCallback(new WebContentDisplayFromStructureCallback(getTargetScreenletId(), structureId, articleId, locale));

		JournalContentConnector journalArticleConnector = ServiceProvider.getInstance().getJournalContentConnector(batchSession);
		journalArticleConnector.getArticle(groupId, articleId);

		DDMStructureConnector structureConnector = ServiceProvider.getInstance().getDDMStructureConnector(batchSession);
		structureConnector.getStructure(structureId);

		batchSession.invoke();
	}

	@Override
	protected boolean cached(Object... args) throws Exception {

		Long structureId = (Long) args[0];
		Long groupId = (Long) args[1];
		String articleId = (String) args[2];
		Locale locale = (Locale) args[3];

		String id = structureId + articleId;
		Long userId = SessionContext.getUserId();
		TableCache tableCache = (TableCache) CacheSQL.getInstance().getById(DefaultCachedType.WEB_CONTENT, id, groupId, userId, locale);
		if (tableCache != null) {
			WebContent webContent = new WebContent(JSONUtil.toMap(new JSONObject(tableCache.getContent())), locale);
			onEvent(new WebContentDisplayEvent(getTargetScreenletId(), structureId, articleId, locale, webContent));
			return true;
		}
		return false;
	}

	@Override
	protected void storeToCache(WebContentDisplayEvent event, Object... args) {
		String webContentId = event.getStructureId() + event.getArticleId();
		String values = new JSONObject(event.getWebContent().getValues()).toString();
		CacheSQL.getInstance().set(new TableCache(webContentId, DefaultCachedType.WEB_CONTENT,
			values, event.getGroupId(), null, event.getLocale()));
	}

	private void validate(Long structureId, long groupId, String articleId, Locale locale) {
		super.validate(locale);

		if (groupId == 0) {
			throw new IllegalArgumentException("GroupId cannot be null");
		}

		if (structureId == null || structureId == 0L) {
			throw new IllegalArgumentException("StructureId cannot be null");
		}

		if (articleId == null || articleId.isEmpty()) {
			throw new IllegalArgumentException("ArticleId cannot be null");
		}
	}
}
