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

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayCallback
	extends InteractorAsyncTaskCallback<String> {

	public WebContentDisplayCallback(int targetScreenletId, long groupId, String articleId, Locale locale, Long templateId) {
		super(targetScreenletId);

		_groupId = groupId;
		_articleId = articleId;
		_locale = locale;
		_templateId = templateId;
	}

	public WebContentDisplayCallback(int targetScreenletId, long groupId, String articleId, Locale locale) {
		this(targetScreenletId, groupId, articleId, locale, null);
	}

	public Long getTemplateId() {
		return _templateId;
	}

	@Override
	public String transform(Object obj) throws Exception {
		return obj.toString();
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, Exception e) {
		return new WebContentDisplayEvent(targetScreenletId, _groupId, _articleId, _locale, _templateId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, String result) {
		return new WebContentDisplayEvent(targetScreenletId, _groupId, _articleId, _locale, _templateId, result);
	}

	private final String _articleId;
	private final long _groupId;
	private final Locale _locale;
	private final Long _templateId;
}