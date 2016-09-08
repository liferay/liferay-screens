package com.liferay.mobile.screens.base.list;

import android.view.View;
import com.liferay.mobile.screens.base.thread.listener.BaseCacheListener;
import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListListener<E> extends BaseCacheListener {

	void onListPageFailed(int startRow, Exception e);

	void onListPageReceived(int startRow, int endRow, List<E> entries, int rowCount);

	void onListItemSelected(E element, View view);
}
