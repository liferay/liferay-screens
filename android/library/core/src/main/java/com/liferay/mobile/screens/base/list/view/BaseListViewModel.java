package com.liferay.mobile.screens.base.list.view;

import com.liferay.mobile.screens.base.list.BaseListScreenlet;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListViewModel<E> {

	public void setListPage(int page, List<E> entries, int rowCount);

}
