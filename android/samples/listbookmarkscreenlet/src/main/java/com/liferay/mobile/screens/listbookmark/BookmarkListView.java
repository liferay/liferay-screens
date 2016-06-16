package com.liferay.mobile.screens.listbookmark;

import android.content.Context;
import android.util.AttributeSet;

import com.liferay.mobile.screens.base.list.BaseListScreenletView;

/**
 * @author Javier Gamarra
 */
public class BookmarkListView
	extends BaseListScreenletView<Bookmark, BookmarkAdapter.BookmarkViewHolder, BookmarkAdapter> {

	public BookmarkListView(Context context) {
		super(context);
	}

	public BookmarkListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public BookmarkListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override
	protected BookmarkAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new BookmarkAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	@Override
	protected int getItemLayoutId() {
		return R.layout.bookmark_row;
	}
}
