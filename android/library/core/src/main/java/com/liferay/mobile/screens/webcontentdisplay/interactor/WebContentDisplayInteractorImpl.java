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
import com.liferay.mobile.android.v62.journalarticle.JournalArticleService;
import com.liferay.mobile.screens.base.interactor.BaseCachedRemoteInteractor;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v62.ScreensjournalarticleService;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayInteractorImpl
	extends BaseCachedRemoteInteractor<WebContentDisplayListener, WebContentDisplayEvent>
	implements WebContentDisplayInteractor {

	public WebContentDisplayInteractorImpl(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy);
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

		onEventWithCache(event, event.getArticleId(), event.getGroupId(), event.getLocale());

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

		if (templateId == null) {
			JournalArticleService service = getJournalArticleService(groupId, articleId, locale);
			service.getArticleContent(groupId, articleId, locale.toString(), null);
		}
		else {
			ScreensjournalarticleService screensjournalarticleService = getScreensJournalArticleService(groupId, articleId, locale);
			screensjournalarticleService.getJournalArticleContent(groupId, articleId, templateId, locale.toString());
		}
	}

	@Override
	protected void notifyError(WebContentDisplayEvent event) {
		getListener().onWebContentFailure(null, event.getException());
	}

	@Override
	protected boolean cached(Object[] args) {

		long groupId = (long) args[0];
		String articleId = (String) args[1];
		Locale locale = (Locale) args[2];

		TableCache webContent = (TableCache) CacheSQL.getInstance().getById(DefaultCachedType.WEB_CONTENT, articleId, groupId, null, locale);
		if (webContent != null) {
			onEvent(new WebContentDisplayEvent(getTargetScreenletId(), groupId, articleId, locale, webContent.getContent()));
			return true;
		}
		return false;
	}

	@Override
	protected void storeToCache(WebContentDisplayEvent event, Object... args) {
		CacheSQL.getInstance().set(new TableCache(event.getArticleId(), DefaultCachedType.WEB_CONTENT,
			event.getHtml(), event.getGroupId(), null, event.getLocale()));
	}

	protected JournalArticleService getJournalArticleService(long groupId, String articleId, Locale locale) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new WebContentDisplayCallback(getTargetScreenletId(), groupId, articleId, locale));
		return new JournalArticleService(session);
	}

	protected ScreensjournalarticleService getScreensJournalArticleService(long groupId, String articleId, Locale locale) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(
			new WebContentDisplayCallback(getTargetScreenletId(), groupId, articleId, locale));

		return new ScreensjournalarticleService(session);
	}

	protected void validate(long groupId, String articleId, Locale locale) {
		if (groupId <= 0) {
			throw new IllegalArgumentException("GroupId cannot be 0 or negative");
		}

		if (articleId == null || articleId.isEmpty()) {
			throw new IllegalArgumentException("ArticleId cannot be empty");
		}

		if (locale == null) {
			throw new IllegalArgumentException("Locale cannot be empty");
		}
	}
}