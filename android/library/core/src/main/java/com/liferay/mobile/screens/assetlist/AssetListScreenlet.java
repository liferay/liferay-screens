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

package com.liferay.mobile.screens.assetlist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractor;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractorImpl;
import com.liferay.mobile.screens.assetlist.interactor.AssetListInteractorListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.cache.OfflinePolicy;
import com.liferay.mobile.screens.context.LiferayServerContext;

import java.util.Locale;

/**
 * @author Silvio Santos
 */
public class AssetListScreenlet
	extends BaseListScreenlet<AssetEntry, AssetListInteractor>
	implements AssetListInteractorListener {

	public AssetListScreenlet(Context context) {
		super(context);
	}

	public AssetListScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public AssetListScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
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
	protected void loadRows(AssetListInteractor interactor, int startRow, int endRow, Locale locale)
		throws Exception {

		interactor.loadRows(_groupId, _classNameId, _portletItemName, startRow, endRow, locale);
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {
		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
			attributes, R.styleable.AssetListScreenlet, 0, 0);
		_classNameId = typedArray.getInt(
			R.styleable.AssetListScreenlet_classNameId, 0);

		int offlinePolicy = typedArray.getInt(R.styleable.WebContentDisplayScreenlet_offlinePolicy,
			OfflinePolicy.REMOTE_ONLY.ordinal());
		_offlinePolicy = OfflinePolicy.values()[offlinePolicy];

		long groupId = LiferayServerContext.getGroupId();

		_groupId = castToLongOrUseDefault(typedArray.getString(
			R.styleable.AssetListScreenlet_groupId), groupId);

		_portletItemName = typedArray.getString(R.styleable.AssetListScreenlet_portletItemName);

		typedArray.recycle();

		return super.createScreenletView(context, attributes);
	}

	@Override
	protected AssetListInteractor createInteractor(String actionName) {
		return new AssetListInteractorImpl(getScreenletId(), _offlinePolicy);
	}

	private OfflinePolicy _offlinePolicy;
	private long _classNameId;
	private long _groupId;
	private String _portletItemName;

}