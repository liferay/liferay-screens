package com.liferay.mobile.screens.base.list;

import java.util.List;

public class BaseListResult<E> {

    private List<E> entries;
    private int rowCount;

    public void setEntries(List<E> entries) {
        this.entries = entries;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public List<E> getEntries() {
        return entries;
    }

    public int getRowCount() {
        return rowCount;
    }
}
