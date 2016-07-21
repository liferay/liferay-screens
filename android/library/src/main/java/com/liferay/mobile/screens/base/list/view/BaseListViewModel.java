package com.liferay.mobile.screens.base.list.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListViewModel<E> extends BaseViewModel {

	void showFinishOperation(int startRow, int endRow, List<E> entries, int rowCount);

	void showFinishOperation(int startRow, int endRow, Exception e);

}
