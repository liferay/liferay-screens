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

import com.liferay.mobile.screens.base.thread.event.OfflineEventNew;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public class BaseListEvent<E> extends OfflineEventNew {

	public BaseListEvent(int startRow, int endRow, List<E> entries, int rowCount) {

		_entries = entries;
		_startRow = startRow;
		_endRow = endRow;
		_rowCount = rowCount;
	}

	public List<E> getEntries() {
		return _entries;
	}

	public int getStartRow() {
		return _startRow;
	}

	public int getEndRow() {
		return _endRow;
	}

	@Override
	public String getId() throws Exception {
		return _startRow + "_" + _endRow;
	}

	public int getRowCount() {
		return _rowCount;
	}

	private List<E> _entries;
	private int _startRow;
	private int _endRow;
	private int _rowCount;
}