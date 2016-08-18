/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mobile.screens.asset.list;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.asset.list.interactor.AssetListInteractorImpl;
import com.liferay.mobile.screens.asset.list.interactor.AssetListInteractorListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;
import java.util.HashMap;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet extends BaseListScreenlet<AssetEntry, AssetListInteractorImpl>
	implements AssetListInteractorListener {

	public AssetListScreenlet(Context context) {
		super(context);
	}

	public AssetListScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AssetListScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public AssetListScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public OfflinePolicy getOfflinePolicy() {
		return _offlinePolicy;
	}

	public void setOfflinePolicy(OfflinePolicy offlinePolicy) {
		_offlinePolicy = offlinePolicy;
	}

	public String getPortletItemName() {
		return _portletItemName;
	}

	public void setPortletItemName(String portletItemName) {
		_portletItemName = portletItemName;
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

	public HashMap<String, Object> getCustomEntryQuery() {
		return _customEntryQuery;
	}

	public void setCustomEntryQuery(HashMap<String, Object> customEntryQuery) {
		_customEntryQuery = customEntryQuery;
	}

	@Override
	protected void loadRows(AssetListInteractorImpl interactor) throws Exception {
		interactor.start(_classNameId, _portletItemName, _customEntryQuery);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.AssetListScreenlet, 0, 0);

		_classNameId = castToLong(typedArray.getString(R.styleable.AssetListScreenlet_classNameId));

		Integer offlinePolicy =
			typedArray.getInteger(R.styleable.AssetListScreenlet_offlinePolicy, OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		_groupId = castToLongOrUseDefault(typedArray.getString(R.styleable.AssetListScreenlet_groupId), groupId);

		_portletItemName = typedArray.getString(R.styleable.AssetListScreenlet_portletItemName);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected AssetListInteractorImpl createInteractor(String actionName) {
		return new AssetListInteractorImpl();
	}

	private OfflinePolicy _offlinePolicy;
	private long _classNameId;
	private long _groupId;
	private String _portletItemName;
	private HashMap<String, Object> _customEntryQuery = new HashMap<>();
}