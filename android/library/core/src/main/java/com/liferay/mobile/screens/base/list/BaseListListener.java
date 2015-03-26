package com.liferay.mobile.screens.base.list;

import android.view.View;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListListener<E> {

	void onListPageFailed(BaseListScreenlet source, int page, Exception e);

	void onListPageReceived(BaseListScreenlet source, int page, List<E> entries, int rowCount);

	void onListItemSelected(E element, View view);
}
