package com.liferay.mobile.screens.listbookmark;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;

/**
 * @author Javier Gamarra
 */
public class BookmarkAdapter extends BaseListAdapter<Bookmark, BookmarkAdapter.BookmarkViewHolder> {

	public BookmarkAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull
	@Override
	public BookmarkViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new BookmarkAdapter.BookmarkViewHolder(view, listener);
	}

	@Override
	protected void fillHolder(Bookmark entry, BookmarkViewHolder holder) {
		holder.bind(entry);
	}

	public class BookmarkViewHolder extends BaseListAdapter.ViewHolder {

		private final TextView url;

		public BookmarkViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			url = view.findViewById(R.id.bookmark_url);
		}

		public void bind(Bookmark entry) {
			url.setText(entry.getUrl());
		}
	}
}
