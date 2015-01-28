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

package com.liferay.mobile.screens.webcontentdisplay;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;

import android.view.LayoutInflater;
import android.view.View;

import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.BaseViewModel;
import com.liferay.mobile.screens.util.LiferayServerContext;
import com.liferay.mobile.screens.webcontentdisplay.interactor.WebContentDisplayInteractor;
import com.liferay.mobile.screens.webcontentdisplay.interactor.WebContentDisplayInteractorImpl;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayScreenlet
		extends BaseScreenlet<BaseViewModel, WebContentDisplayInteractor>
		implements WebContentDisplayListener {

	public WebContentDisplayScreenlet(Context context) {
		this(context, null);
	}

	public WebContentDisplayScreenlet(
		Context context, AttributeSet attributes) {

		this(context, attributes, 0);
	}

	public WebContentDisplayScreenlet(
		Context context, AttributeSet attributes, int defaultStyle) {

		super(context, attributes, defaultStyle);

		setInteractor(new WebContentDisplayInteractorImpl());
	}

	public void load() throws Exception {
		if (_articleId == null) {
			throw new IllegalArgumentException("articleId cannot be null");
		}

		getInteractor().load(_groupId, _articleId);
	}

	@Override
	public void onUserAction(String userActionName) {
	}

	@Override
	public void onWebContentFailure(Exception e) {
		if (_listener != null) {
			_listener.onWebContentFailure(e);
		}

		WebContentDisplayListener listenerView =
			(WebContentDisplayListener)getScreenletView();

		listenerView.onWebContentFailure(e);
	}

	@Override
	public String onWebContentReceived(String html) {
		String modifiedHtml;

		if (_listener != null) {
			modifiedHtml = _listener.onWebContentReceived(html);

			if (modifiedHtml == null) {
				modifiedHtml = html;
			}
		}
		else {
			modifiedHtml = html;
		}

		WebContentDisplayListener listenerView =
			(WebContentDisplayListener)getScreenletView();

		listenerView.onWebContentReceived(modifiedHtml);

		return modifiedHtml;
	}

	public void setListener(WebContentDisplayListener listener) {
		_listener = listener;
	}

	@Override
	protected View createScreenletView(
		Context context, AttributeSet attributes) {

		TypedArray typedArray = context.getTheme().obtainStyledAttributes(
				attributes, R.styleable.WebContentDisplayScreenlet, 0, 0);

		_articleId = typedArray.getString(
			R.styleable.WebContentDisplayScreenlet_articleId);

		_groupId = typedArray.getInt(
				R.styleable.WebContentDisplayScreenlet_groupId,
				(int)LiferayServerContext.getGroupId());

		int layoutId = typedArray.getResourceId(
				R.styleable.WebContentDisplayScreenlet_layoutId, 0);

		View view = LayoutInflater.from(getContext()).inflate(layoutId, null);

		typedArray.recycle();

		return view;
	}

	private String _articleId;
	private long _groupId;
	private WebContentDisplayListener _listener;

}