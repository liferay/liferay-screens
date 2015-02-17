package com.liferay.mobile.screens.base.list;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListListener<E> {

    public void onListPageFailed(BaseListScreenlet source, int page, Exception e);

    public void onListPageReceived(BaseListScreenlet source, int page, List<E> entries, int rowCount);
}
