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
import com.liferay.mobile.screens.util.AssetReader;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayBaseInteractor;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayFromArticleIdInteractor;
import com.liferay.mobile.screens.webcontent.display.interactor.WebContentDisplayFromStructureInteractor;
import com.liferay.mobile.screens.webcontent.display.view.WebContentDisplayViewModel;

/**
 * @author Jose Manuel Navarro
 */
public class WebContentDisplayScreenlet
	extends BaseScreenlet<WebContentDisplayViewModel, WebContentDisplayBaseInteractor>
	implements WebContentDisplayListener {

	public static final String WEB_CONTENT_BY_ARTICLE_ID = "WEB_CONTENT_BY_ARTICLE_ID";
	public static final String WEB_CONTENT_WITH_STRUCTURE = "WEB_CONTENT_WITH_STRUCTURE";
	private Long templateId;
	private String articleId;
	private Long structureId;
	private boolean autoLoad;
	private boolean javascriptEnabled;
	private String labelFields;
	private WebContentDisplayListener listener;
	public WebView.HitTestResult result;
	private int customCssFile = R.raw.webcontent_default;

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

	/**
	 * Searches the {@link WebContent} through {@link #structureId}) and loads it
	 * in the screenlet depending on its value.
	 */
	public void load() {
		performUserAction(structureId != 0 ? WEB_CONTENT_WITH_STRUCTURE : WEB_CONTENT_BY_ARTICLE_ID);
	}

	@Override
	public void error(Exception e, String userAction) {
		getViewModel().showFailedOperation(userAction, e);

		if (listener != null) {
			listener.error(e, userAction);
		}
	}

	@Override
	public boolean onUrlClicked(String url) {
		return listener != null && listener.onUrlClicked(url);
	}

	@Override
	public boolean onWebContentTouched(View view, MotionEvent event) {
		return listener != null && listener.onWebContentTouched(view, event);
	}

	@Override
	public WebContent onWebContentReceived(WebContent webContent) {
		WebContent modifiedHtml = webContent;

		if (listener != null) {
			WebContent listenerHtml = listener.onWebContentReceived(webContent);

			if (listenerHtml != null) {
				modifiedHtml = listenerHtml;
			}
		}

		String css = AssetReader.read(getContext(), customCssFile);
		getViewModel().showFinishOperation(modifiedHtml, css != null ? css : "");

		return modifiedHtml;
	}

	public void setListener(WebContentDisplayListener listener) {
		this.listener = listener;
	}

	public boolean isJavascriptEnabled() {
		return javascriptEnabled;
	}

	public void setJavascriptEnabled(boolean javascriptEnabled) {
		this.javascriptEnabled = javascriptEnabled;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public Long getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}

	public boolean isAutoLoad() {
		return autoLoad;
	}

	public void setAutoLoad(boolean autoLoad) {
		this.autoLoad = autoLoad;
	}

	public String getLabelFields() {
		return labelFields;
	}

	public void setLabelFields(String labelFields) {
		this.labelFields = labelFields;
	}

	public Long getStructureId() {
		return structureId;
	}

	public void setStructureId(Long structureId) {
		this.structureId = structureId;
	}

	public void setCustomCssFile(int customCssFile) {
		this.customCssFile = customCssFile;
	}

	/**
	 * Checks if there is a session created and if exists {@link #articleId} attribute.
	 * Then calls {@link #load()} method.
	 */
	protected void autoLoad() {
		if (SessionContext.isLoggedIn() && articleId != null) {
			load();
		}
	}

	@Override
	protected View createScreenletView(Context context, AttributeSet attributes) {

		TypedArray typedArray =
			context.getTheme().obtainStyledAttributes(attributes, R.styleable.WebContentDisplayScreenlet, 0, 0);

		autoLoad = typedArray.getBoolean(R.styleable.WebContentDisplayScreenlet_autoLoad, true);

		articleId = typedArray.getString(R.styleable.WebContentDisplayScreenlet_articleId);

		templateId = castToLong(typedArray.getString(R.styleable.WebContentDisplayScreenlet_templateId));

		structureId = castToLong(typedArray.getString(R.styleable.WebContentDisplayScreenlet_structureId));

		labelFields = typedArray.getString(R.styleable.WebContentDisplayScreenlet_labelFields);

		javascriptEnabled = typedArray.getBoolean(R.styleable.WebContentDisplayScreenlet_javascriptEnabled, false);

		int layoutId = typedArray.getResourceId(R.styleable.WebContentDisplayScreenlet_layoutId, getDefaultLayoutId());

		typedArray.recycle();

		return LayoutInflater.from(context).inflate(layoutId, null);
	}

	@Override
	protected WebContentDisplayBaseInteractor createInteractor(String actionName) {
		if (WEB_CONTENT_BY_ARTICLE_ID.equals(actionName)) {
			return new WebContentDisplayFromArticleIdInteractor();
		} else {
			return new WebContentDisplayFromStructureInteractor();
		}
	}

	@Override
	protected void onUserAction(String userActionName, WebContentDisplayBaseInteractor interactor, Object... args) {

		locale = getResources().getConfiguration().locale;

		if (WEB_CONTENT_BY_ARTICLE_ID.equals(userActionName)) {
			WebContentDisplayFromArticleIdInteractor interactorFromArticleId =
				(WebContentDisplayFromArticleIdInteractor) getInteractor(userActionName);

			interactorFromArticleId.start(articleId, templateId);
		} else {
			WebContentDisplayFromStructureInteractor interactorFromStructure =
				(WebContentDisplayFromStructureInteractor) getInteractor(userActionName);

			interactorFromStructure.start(articleId, structureId);
		}
	}

	@Override
	protected void onScreenletAttached() {
		if (autoLoad) {
			autoLoad();
		}
	}
}