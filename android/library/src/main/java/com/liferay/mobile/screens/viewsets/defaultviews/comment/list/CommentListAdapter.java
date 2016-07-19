package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListAdapterListener;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentListAdapter extends BaseListAdapter<CommentEntry, CommentListAdapter.CommentViewHolder> {

	public CommentListAdapter(int layoutId, int progressLayoutId, BaseListAdapterListener listener, Context context) {
		super(layoutId, progressLayoutId, listener);
		_context = context;
	}

	@NonNull @Override public CommentViewHolder createViewHolder(View view, BaseListAdapterListener listener) {
		return new CommentViewHolder(view, listener);
	}

	@Override protected void fillHolder(CommentEntry entry, CommentViewHolder holder) {
		holder.bind(entry);
	}

	public void setHtmlBody(boolean htmlBody) {
		this._htmlBody = htmlBody;
	}

	public class CommentViewHolder extends BaseListAdapter.ViewHolder {

		public CommentViewHolder(View view, BaseListAdapterListener listener) {
			super(view, listener);

			_userNameTextView = (TextView) view.findViewById(R.id.comment_user_name);
			_bodyTextView = (TextView) view.findViewById(R.id.comment_body);
			_userPortraitScreenlet = (UserPortraitScreenlet) view.findViewById(R.id.comment_user_portrait);
			_createDateTextView = (TextView) view.findViewById(R.id.comment_create_date);
			_editedTextView = (TextView) view.findViewById(R.id.comment_edited);
			_childCountTextView = (TextView) view.findViewById(R.id.comment_child_number);
		}

		public void bind(CommentEntry entry) {
			_userPortraitScreenlet.setUserId(entry.getUserId());
			_userPortraitScreenlet.load();

			_userNameTextView.setText(entry.getUserName());

			_createDateTextView.setText(entry.getCreateDateAsTimeSpan());

			if (entry.getModifiedDate() != entry.getCreateDate()) {
				_editedTextView.setVisibility(View.VISIBLE);
			} else {
				_editedTextView.setVisibility(View.GONE);
			}

			if (_htmlBody) {
				_bodyTextView.setText(Html.fromHtml(entry.getBody()));
			} else {
				_bodyTextView.setText(Html.fromHtml(entry.getBody()).toString().replaceAll("\n", "").trim());
			}

			int childCount = entry.getChildCount();

			if (childCount > 0) {
				_childCountTextView.setVisibility(View.VISIBLE);
				_childCountTextView.setText(_context.getResources().getQuantityString(
					R.plurals.number_replys, childCount, childCount));
			} else {
				_childCountTextView.setVisibility(View.GONE);
			}
		}

		private final TextView _userNameTextView;
		private final TextView _bodyTextView;
		private final UserPortraitScreenlet _userPortraitScreenlet;
		private final TextView _createDateTextView;
		private final TextView _editedTextView;
		private final TextView _childCountTextView;
	}

	private final Context _context;
	private boolean _htmlBody;
}
