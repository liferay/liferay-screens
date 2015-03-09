package com.liferay.mobile.screens.base.list.interactor;

import java.util.List;

public class BaseListResult<E> {

    public void setEntries(List<E> values) {
        _entries = values;
    }

    public void setRowCount(int value) {
        _rowCount = value;
    }

    public List<E> getEntries() {
        return _entries;
    }

    public int getRowCount() {
        return _rowCount;
    }

	private List<E> _entries;
	private int _rowCount;

}
