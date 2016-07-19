package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

	public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener) {
		super(layoutId, progressLayoutId, listener);
	}

	@NonNull @Override public CommentViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new CommentViewHolder(view, listener);
	}

	@Override protected void fillHolder(CommentEntry entry, CommentViewHolder holder) {
		holder.bind(entry);
	}

	public class CommentViewHolder extends BaseListAdapter.ViewHolder {

		public CommentViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			_userNameTextView = (TextView) view.findViewById(R.id.comment_user_name);
			_bodyTextView = (TextView) view.findViewById(R.id.comment_body);
		}

		public void bind(CommentEntry entry) {
			_userNameTextView.setText(entry.getUserName());
			_bodyTextView.setText(entry.getBody());
		}

		private final TextView _userNameTextView;
		private final TextView _bodyTextView;
	}
}
