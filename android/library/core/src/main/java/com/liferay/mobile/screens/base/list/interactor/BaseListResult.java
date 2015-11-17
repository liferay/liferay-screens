package com.liferay.mobile.screens.base.list.interactor;

import java.util.List;

public class BaseListResult<E> {

	public List<E> getEntries() {
		return _entries;
	}

	public void setEntries(List<E> values) {
		_entries = values;
	}

	public int getRowCount() {
		return _rowCount;
	}

	public void setRowCount(int value) {
		_rowCount = value;
	}

	private List<E> _entries;
	private int _rowCount;

}
