package com.liferay.mobile.screens.viewsets.defaultviews.webcontent.list;

import android.support.annotation.NonNull;
import android.view.View;

import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.webcontent.WebContent;

/**
 * @author Javier Gamarra
 */
public class WebContentListAdapter extends BaseListAdapter<WebContent, WebContentListAdapter.ViewHolder> {

	public WebContentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return null;
	}

	@Override
	protected void fillHolder(WebContent entry, ViewHolder holder) {

	}

	public class ViewHolder extends BaseListAdapter.ViewHolder {
		public ViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);
		}
	}
}
