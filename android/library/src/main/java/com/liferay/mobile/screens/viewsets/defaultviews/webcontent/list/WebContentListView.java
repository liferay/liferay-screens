package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentListView extends BaseListScreenletView<WebContent, WebContentListAdapter.ViewHolder, WebContentListAdapter> {
	public WebContentListView(Context context) {
		super(context);
	}

	public WebContentListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public WebContentListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected WebContentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new WebContentListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

}
