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

package com.liferay.mobile.screens.webcontent.display;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayBaseInteractorImpl;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayFromArticleIdInteractorImpl;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayFromStructureInteractorImpl;
import com.liferay.mobile.screens.webcontent.display.view.WebContentDisplayViewModel;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayScreenlet
	extends BaseScreenlet<WebContentDisplayViewModel, WebContentDisplayBaseInteractorImpl>
	implements WebContentDisplayListener {

	public static final String WEB_CONTENT_BY_ARTICLE_ID = "WEB_CONTENT_BY_ARTICLE_ID";
	public static final String WEB_CONTENT_WITH_STRUCTURE = "WEB_CONTENT_WITH_STRUCTURE";

	public WebContentDisplayScreenlet(Context context) {
		super(context);
	}

	public WebContentDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WebContentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public WebContentDisplayScreenlet(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	public void load() {
		if (_structureId != 0) {
			performUserAction(WEB_CONTENT_WITH_STRUCTURE);
		} else {
			performUserAction(WEB_CONTENT_BY_ARTICLE_ID);
		}
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(userAction, e);

		if (_listener != null) {
			_listener.error(e, userAction);
		}
	}

	@Override
	public void onWebContentClicked(WebView.HitTestResult result, MotionEvent event) {
		if (_listener != null) {
			_listener.onWebContentClicked(result, event);
		}
	}

	@Override
	public WebContent onWebContentReceived(WebContent webContent) {
		WebContent modifiedHtml = webContent;

		if (_listener != null) {
			WebContent listenerHtml = _listener.onWebContentReceived(webContent);

			if (listenerHtml != null) {
				modifiedHtml = listenerHtml;
			}
		}

		getViewModel().showFinishOperation(modifiedHtml);

		return modifiedHtml;
	}

	@Override
	public void loadingFromCache(boolean success) {
		if (_listener != null) {
			_listener.loadingFromCache(success);
		}
	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {
		if (_listener != null) {
			_listener.retrievingOnline(triedInCache, e);
		}
	}

	@Override
	public void storingToCache(Object object) {
		if (_listener != null) {
			_listener.storingToCache(object);
		}
	}

	public void setListener(WebContentDisplayListener listener) {
		_listener = listener;
	}

	public boolean isJavascriptEnabled() {
		return _javascriptEnabled;
	}

	public void setJavascriptEnabled(boolean javascriptEnabled) {
		_javascriptEnabled = javascriptEnabled;
	}

	public String getArticleId() {
		return _articleId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public Long getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(Long templateId) {
		_templateId = templateId;
	}

	public boolean isAutoLoad() {
		return _autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		_autoLoad = autoLoad;
	}

	public String getLabelFields() {
		return _labelFields;
	}

	public void setLabelFields(String labelFields) {
		_labelFields = labelFields;
	}

	public Long getStructureId() {
		return _structureId;
	}

	public void setStructureId(Long structureId) {
		_structureId = structureId;
	}

	protected void autoLoad() {
		if (_articleId != null && SessionContext.isLoggedIn()) {
			load();
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.WebContentDisplayScreenlet, 0, 0);

		_autoLoad = typedArray.getBoolean(R.styleable.WebContentDisplayScreenlet_autoLoad, true);

		_articleId = typedArray.getString(R.styleable.WebContentDisplayScreenlet_articleId);

		_templateId = castToLong(typedArray.getString(R.styleable.WebContentDisplayScreenlet_templateId));

		_structureId = castToLong(typedArray.getString(R.styleable.WebContentDisplayScreenlet_structureId));

		_labelFields = typedArray.getString(R.styleable.WebContentDisplayScreenlet_labelFields);

		_javascriptEnabled = typedArray.getBoolean(R.styleable.WebContentDisplayScreenlet_javascriptEnabled, false);

		int layoutId = typedArray.getResourceId(R.styleable.WebContentDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected WebContentDisplayBaseInteractorImpl createInteractor(String actionName) {
		if (WEB_CONTENT_BY_ARTICLE_ID.equals(actionName)) {
			return new WebContentDisplayFromArticleIdInteractorImpl();
		} else {
			return new WebContentDisplayFromStructureInteractorImpl();
		}
	}

	@Override
	protected void onUserAction(String userActionName, WebContentDisplayBaseInteractorImpl interactor, Object... args) {

		locale = getResources().getConfiguration().locale;

		if (WEB_CONTENT_BY_ARTICLE_ID.equals(userActionName)) {
			WebContentDisplayFromArticleIdInteractorImpl interactorFromArticleId =
				(WebContentDisplayFromArticleIdInteractorImpl) getInteractor(userActionName);

			interactorFromArticleId.start(_articleId, _templateId);
		} else {
			WebContentDisplayFromStructureInteractorImpl interactorFromStructure =
				(WebContentDisplayFromStructureInteractorImpl) getInteractor(userActionName);

			interactorFromStructure.start(_articleId, _structureId);
		}
	}

	@Override
	protected void onScreenletAttached() {
		if (_autoLoad) {
			autoLoad();
		}
	}

	private Long _templateId;
	private String _articleId;
	private Long _structureId;
	private boolean _autoLoad;
	private boolean _javascriptEnabled;
	private String _labelFields;
	private WebContentDisplayListener _listener;
}