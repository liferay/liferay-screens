package com.liferay.mobile.screens.base.list.view;

import com.liferay.mobile.screens.base.view.BaseViewModel;

import java.util.List;

/**
 * @author Javier Gamarra
 */
public interface BaseListViewModel<E> extends BaseViewModel {

	void showFinishOperation(int page, List<E> entries, int rowCount);
	void showFinishOperation(int page, Exception e);

}
