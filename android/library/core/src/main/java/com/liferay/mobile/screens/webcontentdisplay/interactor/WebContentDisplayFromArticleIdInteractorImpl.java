/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.webcontentdisplay.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontentdisplay.operation.JournalContentOperation;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayFromArticleIdInteractorImpl
	extends WebContentDisplayBaseInteractorImpl
	implements WebContentDisplayFromArticleIdInteractor {

	public WebContentDisplayFromArticleIdInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void load(long groupId, String articleId, Long templateId, Locale locale)
		throws Exception {

		validate(groupId, articleId, locale);

		processWithCache(groupId, articleId, locale, templateId);
	}

	public void onEvent(WebContentDisplayEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event, event.getGroupId(), event.getArticleId(), event.getLocale(), event.getTemplateId());

		if (!event.isFailed()) {
			getListener().onWebContentReceived(null, event.getHtml());
		}
	}

	@Override
	protected void online(Object[] args) throws Exception {

		long groupId = (long) args[0];
		String articleId = (String) args[1];
		Locale locale = (Locale) args[2];
		Long templateId = (Long) args[3];

		if (templateId == null || templateId == 0) {
			JournalContentOperation service = getJournalArticleService(groupId, articleId, locale);
			service.getArticleContent(groupId, articleId, locale.toString(), null);
		}
		else {
			ScreensJournalContentOperation screensjournalarticleService = getScreensJournalArticleService(groupId, articleId, locale, templateId);
			screensjournalarticleService.getJournalArticleContent(groupId, articleId, templateId, locale.toString());
		}
	}

	@Override
	protected boolean cached(Object[] args) {

		long groupId = (long) args[0];
		String articleId = (String) args[1];
		Locale locale = (Locale) args[2];
		Long templateId = (Long) args[3];

		String id = articleId + (templateId == null || templateId == 0 ? "" : templateId);
		Long userId = SessionContext.getUserId();
		TableCache webContent = (TableCache) CacheSQL.getInstance().getById(DefaultCachedType.WEB_CONTENT, id, groupId, userId, locale);
		if (webContent != null) {
			onEvent(new WebContentDisplayEvent(getTargetScreenletId(), groupId, articleId, locale, templateId, webContent.getContent()));
			return true;
		}
		return false;
	}

	@Override
	protected void storeToCache(WebContentDisplayEvent event, Object... args) {
		String templateString = event.getTemplateId() == null || event.getTemplateId() == 0
			? "" : event.getTemplateId().toString();
		String webContentId = event.getArticleId() + templateString;
		CacheSQL.getInstance().set(new TableCache(webContentId, DefaultCachedType.WEB_CONTENT,
			event.getHtml(), event.getGroupId(), null, event.getLocale()));
	}

	protected JournalContentOperation getJournalArticleService(long groupId, String articleId, Locale locale) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new WebContentDisplayFromArticleIdCallback(getTargetScreenletId(), groupId, articleId, locale));
		return ServiceProvider.getInstance().getJournalContentOperation(session);
	}

	protected ScreensJournalContentOperation getScreensJournalArticleService(long groupId, String articleId, Locale locale, Long templateId) {
		Session session = SessionContext.createSessionFromCurrentSession();
		WebContentDisplayFromArticleIdCallback callback =
			new WebContentDisplayFromArticleIdCallback(getTargetScreenletId(), groupId, articleId, locale, templateId);
		session.setCallback(callback);
		return ServiceProvider.getInstance().getScreensJournalContentOperation(session);
	}

	protected void validate(long groupId, String articleId, Locale locale) {
		super.validate(locale);

		if (groupId <= 0) {
			throw new IllegalArgumentException("GroupId cannot be 0 or negative");
		}

		if (articleId == null || articleId.isEmpty()) {
			throw new IllegalArgumentException("ArticleId cannot be empty");
		}
	}
}