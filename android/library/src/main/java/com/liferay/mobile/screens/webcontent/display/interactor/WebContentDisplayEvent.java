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

import com.liferay.mobile.screens.base.list.interactor.ListEvent;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayEvent extends ListEvent<WebContent> {

    private WebContent webContent;

    public WebContentDisplayEvent() {
        super();
    }

    public WebContentDisplayEvent(String html) {
        webContent = new WebContent(html);
    }

    public WebContentDisplayEvent(WebContent webContent) {
        this.webContent = webContent;
    }

    @Override
    public String getListKey() {
        return webContent.getArticleId();
    }

    @Override
    public WebContent getModel() {
        return webContent;
    }

    public WebContent getWebContent() {
        return webContent;
    }
}