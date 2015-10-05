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

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayEvent extends BasicEvent {

	public WebContentDisplayEvent(int targetScreenletId, long groupId, String articleId, Locale locale, Long templateId, Exception e) {
		super(targetScreenletId, e);

		_articleId = articleId;
		_groupId = groupId;
		_locale = locale;
		_templateId = templateId;
	}

	public WebContentDisplayEvent(int targetScreenletId, long groupId, String articleId, Locale locale, Long templateId, String html) {
		super(targetScreenletId);
		_html = html;

		_articleId = articleId;
		_groupId = groupId;
		_locale = locale;
		_templateId = templateId;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public Locale getLocale() {
		return _locale;
	}

	public String getHtml() {
		return _html;
	}

	public String getArticleId() {
		return _articleId;
	}

	public Long getTemplateId() {
		return _templateId;
	}

	private String _html;
	private String _articleId;
	private long _groupId;
	private Locale _locale;
	private Long _templateId;
}