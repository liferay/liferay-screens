/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.base.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractor;
import com.liferay.mobile.screens.base.list.interactor.BaseListInteractorListener;
import com.liferay.mobile.screens.base.list.interactor.Query;
import com.liferay.mobile.screens.base.list.view.BaseListViewModel;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Silvio Santos
 */
public abstract class BaseListScreenlet<E, N extends BaseListInteractor> extends BaseScreenlet<BaseListViewModel, N>
	implements BaseListInteractorListener<E> {

	public BaseListScreenlet(Context context) {
		super(context);
	}

	public BaseListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public BaseListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public BaseListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void loadPageForRow(int row) {
		loadPage(getPageFromRow(row));
	}

	@Override
	public void onListRowsFailure(int startRow, int endRow, Exception e) {
		getViewModel().showFinishOperation(startRow, endRow, e);

		if (_listener != null) {
			_listener.onListPageFailed(startRow, e);
		}
	}

	@Override
	public void onListRowsReceived(int startRow, int endRow, List<E> entries, int rowCount) {
		getViewModel().showFinishOperation(startRow, endRow, entries, rowCount);

		if (_listener != null) {
			_listener.onListPageReceived(startRow, endRow, entries, rowCount);
		}
	}

	public int getFirstRowForPage(int page) {
		if (page == 0) {
			return 0;
		}

		return (_firstPageSize + (page - 1) * _pageSize);
	}

	public int getPageFromRow(int row) {
		if (row < _firstPageSize) {
			return 0;
		}

		return ((row - _firstPageSize) / _pageSize) + 1;
	}

	public void loadPage(int page) {
		if (page == 0) {
			getViewModel().showStartOperation(null);
		}

		int startRow = getFirstRowForPage(page);
		int endRow = getFirstRowForPage(page + 1);

		try {
			N interactor = getInteractor();
			interactor.setQuery(new Query(startRow, endRow, _obcClassName));
			loadRows(interactor);
		} catch (Exception e) {
			onListRowsFailure(startRow, endRow, e);
		}
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		_autoLoad = autoLoad;
	}

	public int getFirstPageSize() {
		return _firstPageSize;
	}

	public void setFirstPageSize(int firstPageSize) {
		_firstPageSize = firstPageSize;
	}

	public BaseListListener<E> getListener() {
		return _listener;
	}

	public void setListener(BaseListListener<E> listener) {
		_listener = listener;
	}

	public int getPageSize() {
		return _pageSize;
	}

	public void setPageSize(int pageSize) {
		_pageSize = pageSize;
	}

	public List<String> getLabelFields() {
		return _labelFields;
	}

	public void setLabelFields(List<String> labelFields) {
		_labelFields = labelFields;
	}

	protected abstract void loadRows(N interactor) throws Exception;

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetListScreenlet, 0, 0);

		int layoutId = typedArray.getResourceId(R.styleable.AssetListScreenlet_layoutId, getDefaultLayoutId());

		_firstPageSize = typedArray.getInteger(R.styleable.AssetListScreenlet_firstPageSize, _FIRST_PAGE_SIZE);

		_pageSize = typedArray.getInteger(R.styleable.AssetListScreenlet_pageSize, _PAGE_SIZE);

		_autoLoad = typedArray.getBoolean(R.styleable.AssetListScreenlet_autoLoad, true);

		_labelFields = parse(typedArray.getString(R.styleable.AssetListScreenlet_labelFields));

		_obcClassName = typedArray.getString(R.styleable.AssetListScreenlet_obcClassName);

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected void onScreenletAttached() {
		super.onScreenletAttached();

		if (_autoLoad) {
			//LMW-176 TODO handle when first page is already loaded
			loadPage(0);
		}
	}

	@Override
	protected void onUserAction(String userActionName, N interactor, Object... args) {
	}

	private List<String> parse(String labelFields) {
		if (labelFields == null) {
			LiferayLogger.e("No labelFields configured");
			labelFields = "";
		}

		List<String> parsedFields = new ArrayList<>();
		for (String text : labelFields.split(",")) {
			parsedFields.add(text.trim());
		}
		return parsedFields;
	}

	protected static final int _FIRST_PAGE_SIZE = 50;
	protected static final int _PAGE_SIZE = 25;

	protected boolean _autoLoad;
	protected int _firstPageSize;
	protected BaseListListener<E> _listener;
	protected int _pageSize;
	protected String _obcClassName;
	private List<String> _labelFields;
}