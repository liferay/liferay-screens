package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, BaseListAdapter.ViewHolder> {

	public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull @Override public ViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new ViewHolder(view, listener);
	}

	@Override protected void fillHolder(CommentEntry entry, ViewHolder holder) {
		holder.textView.setText(entry.getBody());
	}
}
