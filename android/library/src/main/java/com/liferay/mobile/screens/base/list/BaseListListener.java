package com.liferay.mobile.screens.base.list;

import android.view.View;

import com.liferay.mobile.screens.cache.CacheListener;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListListener<E> extends CacheListener {

	void onListPageFailed(BaseListScreenlet source, int startRow, int endRow, Exception e);

	void onListPageReceived(BaseListScreenlet source, int startRow, int endRow, List<E> entries, int rowCount);

	void onListItemSelected(E element, View view);
}
