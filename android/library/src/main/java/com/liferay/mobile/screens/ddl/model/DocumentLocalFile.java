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

package com.liferay.mobile.screens.ddl.model;

import com.liferay.mobile.screens.util.AndroidUtil;

/**
 * @author Javier Gamarra
 */
public class DocumentLocalFile extends DocumentFile {

    private final String path;

    public DocumentLocalFile(String path) {
        this.path = path;
    }

    @Override
    public String toData() {
        throw new AssertionError("it's not possible to sent the local file, so fast-fail");
    }

    @Override
    public String toString() {
        return path;
    }

    @Override
    public boolean isValid() {
        return path != null && !path.isEmpty();
    }

    @Override
    public String getFileName() {
        if (path == null) {
            return "";
        }

        return AndroidUtil.getFileNameFromPath(path);
    }
}