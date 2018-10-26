/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.web.util;

/**
 * @author Sarai Díaz García
 */
public class RemoteCssScript implements InjectableScript {

    private String name;
    private String content;

    public RemoteCssScript(String name, String url) {
        this.name = name;
        content = "var link = document.createElement('link');"
            + "link.type = 'text/css';"
            + "link.rel = 'stylesheet';"
            + "link.href = '"
            + url
            + "';"
            + "var head = document.getElementsByTagName('head')[0];"
            + "head.appendChild(link);";
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getContent() {
        return content;
    }
}
