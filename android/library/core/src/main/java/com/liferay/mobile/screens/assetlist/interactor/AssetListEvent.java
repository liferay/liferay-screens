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

package com.liferay.mobile.screens.assetlist.interactor;

import com.liferay.mobile.screens.assetlist.AssetListScreenletEntry;
import com.liferay.mobile.screens.base.interactor.BasicEvent;

import java.util.List;

/**
 * @author Silvio Santos
 */
public class AssetListEvent extends BasicEvent {

	public AssetListEvent(int targetScreenletId, Exception e) {
		super(targetScreenletId);
	}

	public AssetListEvent(
		int targetScreenletId, int page, List<AssetListScreenletEntry> entries,
		int rowCount) {

		super(targetScreenletId);

		_entries = entries;
		_page = page;
		_rowCount = rowCount;
	}

	public List<AssetListScreenletEntry> getEntries() {
		return _entries;
	}

	public int getPage() {
		return _page;
	}

	public int getRowCount() {
		return _rowCount;
	}

	private List<AssetListScreenletEntry> _entries;
	private int _page;
	private int _rowCount;

}