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
import com.liferay.mobile.screens.base.interactor.CacheCallback;
import com.liferay.mobile.screens.cache.Cache;
import com.liferay.mobile.screens.cache.CachePolicy;
import com.liferay.mobile.screens.cache.DefaultCachedType;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.cache.sql.CacheSQL;
import com.liferay.mobile.screens.cache.tablecache.TableCache;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayInteractorImpl
	extends BaseCachedRemoteInteractor<WebContentDisplayListener, WebContentDisplayEvent>
	implements WebContentDisplayInteractor {

	public WebContentDisplayInteractorImpl(int targetScreenletId, CachePolicy cachePolicy) {
		super(targetScreenletId, cachePolicy, OfflinePolicy.NO_OFFLINE);
	}

	public void load(final long groupId, final String articleId, final Locale locale)
		throws Exception {

		loadWithCache(new CacheCallback() {
			@Override
			public void loadOnline() throws Exception {
				validate(groupId, articleId, locale);

				JournalArticleService service = getJournalArticleService(groupId, articleId, locale);
				service.getArticleContent(groupId, articleId, locale.toString(), null);
			}

			@Override
			public boolean retrieveFromCache() {
				return loadFromCache(groupId, articleId, locale);
			}
		});
	}

	public void onEvent(WebContentDisplayEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onWebContentFailure(null, event.getException());
		}
		else {
			store(event);

			getListener().onWebContentReceived(null, event.getHtml());
		}

	}

	protected boolean loadFromCache(long groupId, String articleId, Locale locale) {
		Cache cache = CacheSQL.getInstance();
		TableCache webContent = (TableCache) cache.getById(DefaultCachedType.WEB_CONTENT, articleId);
		if (webContent != null) {
			onEvent(new WebContentDisplayEvent(getTargetScreenletId(), webContent.getContent(), articleId, groupId, locale));
			return true;
		}
		return false;
	}

	protected void store(WebContentDisplayEvent event) {
		long userId = SessionContext.getLoggedUser().getId();

		Cache cache = CacheSQL.getInstance();
		cache.set(new TableCache(event.getArticleId(), DefaultCachedType.WEB_CONTENT,
			event.getHtml(), userId, event.getGroupId(), event.getLocale()));
	}

	protected JournalArticleService getJournalArticleService(long groupId, String articleId, Locale locale) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new WebContentDisplayCallback(getTargetScreenletId(), groupId, articleId, locale));
		return new JournalArticleService(session);
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