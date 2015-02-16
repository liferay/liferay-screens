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

package com.liferay.mobile.screens.ddl.list.interactor;

import com.liferay.mobile.screens.assetlist.AssetEntry;
import com.liferay.mobile.screens.base.interactor.BasicEvent;
import com.liferay.mobile.screens.ddl.list.DDLEntry;

import java.util.List;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListEvent extends BasicEvent {

	public DDLListEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId);
	}

	public DDLListEvent(
            int targetScreenletId, int startRow, int endRow, List<DDLEntry> entries,
            int rowCount) {

		super(targetScreenletId);

		_entries = entries;
		_startRow = startRow;
		_endRow = endRow;
		_rowCount = rowCount;
	}

	public List<DDLEntry> getEntries() {
		return _entries;
	}

	public int getStartRow() {
		return _startRow;
	}

	public int getEndRow() {
		return _endRow;
	}

	public int getRowCount() {
		return _rowCount;
	}

	private List<DDLEntry> _entries;
	private int _startRow;
	private int _endRow;
	private int _rowCount;

}