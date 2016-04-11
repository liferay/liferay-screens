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

package com.liferay.mobile.screens.webcontent.display.interactor;

import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.base.interactor.InteractorAsyncTaskCallback;

import java.util.Locale;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayFromClassPKCallback
	extends InteractorAsyncTaskCallback<String> {

	public WebContentDisplayFromClassPKCallback(int targetScreenletId, long classPK, Locale locale, Long templateId) {
		super(targetScreenletId);

		_classPK = classPK;
		_locale = locale;
		_templateId = templateId;
	}

	public WebContentDisplayFromClassPKCallback(int targetScreenletId, long classPK, Locale locale) {
		this(targetScreenletId, classPK, locale, null);
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
		return new WebContentDisplayEvent(targetScreenletId, _classPK, _locale, _templateId, e);
	}

	@Override
	protected BasicEvent createEvent(int targetScreenletId, String result) {
		return new WebContentDisplayEvent(targetScreenletId, _classPK, _locale, _templateId, result);
	}

	private final long _classPK;
	private final Locale _locale;
	private final Long _templateId;
}