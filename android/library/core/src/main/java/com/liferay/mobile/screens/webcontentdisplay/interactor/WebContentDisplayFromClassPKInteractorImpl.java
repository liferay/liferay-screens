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
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.util.ServiceProvider;
import com.liferay.mobile.screens.webcontentdisplay.operation.ScreensJournalContentOperation;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayFromClassPKInteractorImpl
	extends WebContentDisplayBaseInteractorImpl
	implements WebContentDisplayFromClassPKInteractor {

	public WebContentDisplayFromClassPKInteractorImpl(int targetScreenletId, OfflinePolicy offlinePolicy) {
		super(targetScreenletId, offlinePolicy);
	}

	public void load(long classPK, Long templateId, Locale locale)
		throws Exception {

		validate(classPK, locale);

		processWithCache(classPK, templateId, locale);
	}

	public void onEvent(WebContentDisplayEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		onEventWithCache(event, event.getClassPK(), event.getLocale(), event.getTemplateId());

		if (!event.isFailed()) {
			getListener().onWebContentReceived(null, event.getHtml());
		}
	}

	@Override
	protected void online(Object[] args) throws Exception {

		long classPK = (long) args[0];
		long templateId = (long) args[1];
		Locale locale = (Locale) args[2];

		ScreensJournalContentOperation screensjournalarticleService = getScreensJournalArticleService(classPK, locale, templateId);

		if (templateId == 0) {
			screensjournalarticleService.getJournalArticleContent(classPK, locale.toString());
		}
		else {
			screensjournalarticleService.getJournalArticleContent(classPK, templateId, locale.toString());
		}
	}

	@Override
	protected boolean cached(Object[] args) {

		long classPK = (long) args[0];
		long templateId = (long) args[1];
		Locale locale = (Locale) args[2];

		String id = String.valueOf(classPK) + (templateId == 0 ? "" : templateId);
		Long userId = SessionContext.getUserId();
		long groupId = LiferayServerContext.getGroupId();
		TableCache webContent = (TableCache) CacheSQL.getInstance().getById(DefaultCachedType.WEB_CONTENT, id, groupId, userId, locale);
		if (webContent != null) {
			onEvent(new WebContentDisplayEvent(getTargetScreenletId(), classPK, locale, templateId, webContent.getContent()));
			return true;
		}
		return false;
	}

	@Override
	protected void storeToCache(WebContentDisplayEvent event, Object... args) {
		String templateString = event.getTemplateId() == 0 ? "" : event.getTemplateId().toString();
		String webContentId = event.getClassPK() + templateString;
		CacheSQL.getInstance().set(new TableCache(webContentId, DefaultCachedType.WEB_CONTENT,
			event.getHtml(), event.getGroupId(), null, event.getLocale()));
	}

	protected ScreensJournalContentOperation getScreensJournalArticleService(long classPK, Locale locale, Long templateId) {
		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new WebContentDisplayFromClassPKCallback(getTargetScreenletId(), classPK, locale, templateId));
		return ServiceProvider.getInstance().getScreensJournalContentOperation(session);
	}

	private void validate(long classPK, Locale locale) {
		super.validate(locale);

		if (classPK <= 0) {
			throw new IllegalArgumentException("ClassPK cannot be 0 or negative");
		}
	}
}