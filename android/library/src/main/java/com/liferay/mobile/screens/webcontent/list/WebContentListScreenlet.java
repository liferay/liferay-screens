package com.liferay.mobile.screens.webcontent.list;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.list.interactor.WebContentListInteractor;

import java.util.Locale;

/**
 * @author Javier Gamarra
 */
public class WebContentListScreenlet extends BaseListScreenlet<WebContent, WebContentListInteractor> {

	public WebContentListScreenlet(Context context) {
		super(context);
	}

	public WebContentListScreenlet(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public WebContentListScreenlet(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	public void loadingFromCache(boolean success) {

	}

	@Override
	public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override
	public void storingToCache(Object object) {

	}

	@Override
	protected void loadRows(WebContentListInteractor interactor, int startRow, int endRow, Locale locale) throws Exception {

	}

	@Override
	protected WebContentListInteractor createInteractor(String actionName) {
		return null;
	}
}
