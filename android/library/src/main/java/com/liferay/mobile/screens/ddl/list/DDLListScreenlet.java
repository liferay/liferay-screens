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

package com.liferay.mobile.screens.ddl.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractorImpl;
import com.liferay.mobile.screens.ddl.list.interactor.DDLListInteractorListener;
import com.liferay.mobile.screens.ddl.model.Record;

/**
 * @author Javier Gamarra
 * @author Silvio Santos
 */
public class DDLListScreenlet extends BaseListScreenlet<Record, DDLListInteractorImpl>
	implements DDLListInteractorListener {

	public DDLListScreenlet(Context context) {
		super(context);
	}

	public DDLListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public DDLListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public DDLListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public long getRecordSetId() {
		return _recordSetId;
	}

	public void setRecordSetId(long recordSetId) {
		_recordSetId = recordSetId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		_offlinePolicy = offlinePolicy;
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	protected void loadRows(DDLListInteractorImpl interactor) throws Exception {
		interactor.start(_recordSetId, _userId);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.DDLListScreenlet, 0, 0);

		int offlinePolicy =
			typedArray.getInt(R.styleable.DDLListScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		_recordSetId = castToLong(typedArray.getString(R.styleable.DDLListScreenlet_recordSetId));
		_userId = castToLong(typedArray.getString(R.styleable.DDLListScreenlet_userId));
		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected DDLListInteractorImpl createInteractor(String actionName) {
		return new DDLListInteractorImpl();
	}

	private long _recordSetId;
	private long _userId;
	private OfflinePolicy _offlinePolicy;
}