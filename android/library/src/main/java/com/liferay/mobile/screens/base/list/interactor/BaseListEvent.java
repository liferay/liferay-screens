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

package com.liferay.mobile.screens.base.list.interactor;

import com.liferay.mobile.screens.base.interactor.event.CacheEvent;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class BaseListEvent<E> extends CacheEvent {

    private List<E> entries;
    private int rowCount;
    private Query query;

    public BaseListEvent() {
        super();
    }

    public BaseListEvent(Query query, List<E> entries, int rowCount) {
        this.query = query;
        this.entries = entries;
        this.rowCount = rowCount;
    }

    public List<E> getEntries() {
        return entries;
    }

    public void setEntries(List<E> entries) {
        this.entries = entries;
    }

    public int getStartRow() {
        return query.getStartRow();
    }

    public int getEndRow() {
        return query.getEndRow();
    }

    public int getRowCount() {
        return rowCount;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }
}