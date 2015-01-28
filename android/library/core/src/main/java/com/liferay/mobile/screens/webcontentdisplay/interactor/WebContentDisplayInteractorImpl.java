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
import com.liferay.mobile.screens.util.EventBusUtil;
import com.liferay.mobile.screens.util.SessionContext;
import com.liferay.mobile.screens.webcontentdisplay.WebContentDisplayListener;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayInteractorImpl
	implements WebContentDisplayInteractor {

	public void load(long groupId, String articleId) throws Exception {
		JournalArticleService service = getJournalArticleService();

		//TODO get languageId from global context
		service.getArticleContent(groupId, articleId, "en_US", null);
	}

	public void onEvent(WebContentDisplayEvent event) {
		if (_listener == null) {
			return;
		}

		String receivedHtml = event.getHtml();

		if (receivedHtml != null) {
			_listener.onWebContentReceived(receivedHtml);
		}
		else {
			_listener.onWebContentFailure(event.getException());
		}
	}

	@Override
	public void onScreenletAttachted(WebContentDisplayListener listener) {
		_listener = listener;

		EventBusUtil.register(this);
	}

	@Override
	public void onScreenletDetached(WebContentDisplayListener listener) {
		EventBusUtil.unregister(this);

		_listener = null;
	}

	protected JournalArticleService getJournalArticleService() {
		if (!SessionContext.hasSession()) {
			throw new IllegalStateException("You need to be logged in to get the Journal Article");
		}

		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new WebContentDisplayCallback());

		return new JournalArticleService(session);
	}

	private String _html;
	private WebContentDisplayListener _listener;

}