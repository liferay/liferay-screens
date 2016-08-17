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

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayEvent extends OfflineEventNew {

	private String id;

	public WebContentDisplayEvent() {
		super();
	}

	@Override
	public String getId() throws Exception {
		return id;
	}

	public WebContentDisplayEvent(String articleId, Long templateId, String html) {
		_webContent = new WebContent(html);

		_articleId = articleId;
		_templateId = templateId;
		id = _articleId + (_templateId == null ? "-" : templateId);
	}

	public WebContentDisplayEvent(Long structureId, String articleId, WebContent webContent) {
		_webContent = webContent;

		_structureId = structureId;
		_articleId = articleId;
		id = _articleId + _structureId;
	}

	public String getArticleId() {
		return _articleId;
	}

	public Long getTemplateId() {
		return _templateId;
	}

	public Long getStructureId() {
		return _structureId;
	}

	public void setStructureId(Long structureId) {
		_structureId = structureId;
	}

	public WebContent getWebContent() {
		return _webContent;
	}

	private String _articleId;
	private Long _templateId;
	private Long _structureId;
	private WebContent _webContent;
}