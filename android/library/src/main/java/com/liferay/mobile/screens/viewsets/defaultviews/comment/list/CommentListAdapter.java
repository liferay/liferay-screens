package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.comment.list.view.CommentView;
import com.liferay.mobile.screens.comment.list.view.CommentViewListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

	public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener, Context context) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull @Override public CommentViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new CommentViewHolder(view, listener);
	}

	@Override protected void fillHolder(CommentEntry entry, CommentViewHolder holder) {
		holder.bind(entry);
	}

	public void setHtmlBody(boolean htmlBody) {
		this._htmlBody = htmlBody;
		this.notifyDataSetChanged();
	}

	public class CommentViewHolder extends BaseListAdapter.ViewHolder {

		public CommentViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);
			_commentView = (CommentView) view.findViewById(R.id.comment_view);
			_commentView.setHtmlBody(_htmlBody);
			_commentView.setListener((CommentViewListener) listener);
		}

		public void bind(CommentEntry entry) {
			_commentView.setCommentEntry(entry);
		}

		private final CommentView _commentView;
	}

	private boolean _htmlBody;
}
