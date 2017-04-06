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

package com.liferay.mobile.screens.portlet.interactor;

import com.liferay.mobile.screens.base.interactor.BaseCacheReadInteractor;
import com.liferay.mobile.screens.portlet.PortletDisplayListener;
import com.liferay.mobile.screens.portlet.PortletDisplayScreenlet;
import com.squareup.okhttp.HttpUrl;

import static com.liferay.mobile.screens.cache.Cache.SEPARATOR;

/**
 * @author Sarai Díaz García
 */

public class PortletDisplayInteractor extends BaseCacheReadInteractor<PortletDisplayListener, PortletEvent> {

	private String url;

	@Override
	public PortletEvent execute(Object... args) throws Exception {

		url = (String) args[0];
		HttpUrl finalUrl = HttpUrl.parse(url);
		return new PortletEvent(finalUrl.toString());
	}

	@Override
	public void onFailure(PortletEvent event) {
		getListener().error(event.getException(), PortletDisplayScreenlet.DEFAULT_ACTION);
	}

	@Override
	public void onSuccess(PortletEvent event) {
		getListener().onRetrievePortletSuccess(url);
	}

	@Override
	protected String getIdFromArgs(Object... args) {
		String url = (String) args[0];
		return (url == null ? SEPARATOR : url);
	}
}
