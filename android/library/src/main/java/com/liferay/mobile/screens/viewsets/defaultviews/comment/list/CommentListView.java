package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.text.Html;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;

/**
 * @author Alejandro Hernández
 */
public class CommentListView
	extends BaseListScreenletView<CommentEntry, CommentListAdapter.CommentViewHolder, CommentListAdapter>
	implements CommentListViewModel {

	public CommentListView(Context context) {
		super(context);
	}

	public CommentListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public CommentListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		_discussionCommentLayout = (ViewGroup) findViewById(R.id.discussion_comment);
		_discussionSeparator = (ImageView) findViewById(R.id.comment_separator);

		_userNameTextView = (TextView) _discussionCommentLayout.findViewById(R.id.comment_user_name);
		_bodyTextView = (TextView) _discussionCommentLayout.findViewById(R.id.comment_body);
		_userPortraitScreenlet = (UserPortraitScreenlet) _discussionCommentLayout.findViewById(R.id.comment_user_portrait);
		_createDateTextView = (TextView) _discussionCommentLayout.findViewById(R.id.comment_create_date);
		_editedTextView = (TextView) _discussionCommentLayout.findViewById(R.id.comment_edited);

		_discussionCommentLayout.findViewById(R.id.row_arrow).setVisibility(GONE);
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && _discussionComment != null) {
			clearAdapterEntries();
			getScreenlet().performUserAction(BaseScreenlet.DEFAULT_ACTION, CommentListScreenlet.OUT_DISCUSSION_ACTION);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override protected CommentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new CommentListAdapter(itemLayoutId, itemProgressLayoutId, this, getContext());
	}

	@Override protected int getItemLayoutId() {
		return R.layout.comment_row_default;
	}

	@Override public void setHtmlBody(boolean htmlBody) {
		_htmlBody = htmlBody;
		getAdapter().setHtmlBody(htmlBody);
	}

	@Override
	public void changeToCommentDiscussion(CommentEntry newRootComment) {
		_discussionComment = newRootComment;

		if (newRootComment == null) {
			_discussionCommentLayout.setVisibility(GONE);
			_discussionSeparator.setVisibility(GONE);
		} else {
			_userPortraitScreenlet.setUserId(newRootComment.getUserId());
			_userPortraitScreenlet.load();

			_userNameTextView.setText(newRootComment.getUserName());

			_createDateTextView.setText(newRootComment.getCreateDateAsTimeSpan());

			if (newRootComment.getModifiedDate() != newRootComment.getCreateDate()) {
				_editedTextView.setVisibility(View.VISIBLE);
			} else {
				_editedTextView.setVisibility(View.GONE);
			}

			if (_htmlBody) {
				_bodyTextView.setText(Html.fromHtml(newRootComment.getBody()));
			} else {
				_bodyTextView.setText(Html.fromHtml(newRootComment.getBody()).toString().replaceAll("\n", "").trim());
			}

			_discussionCommentLayout.setVisibility(VISIBLE);
			_discussionSeparator.setVisibility(VISIBLE);
		}
	}

	@Override
	public void onItemClick(int position, View view) {
		super.onItemClick(position, view);

		setFocusableInTouchMode(true);
		requestFocus();

		CommentEntry newRootComment = getAdapter().getEntries().get(position);

		clearAdapterEntries();

		getScreenlet().performUserAction(BaseScreenlet.DEFAULT_ACTION, CommentListScreenlet.IN_DISCUSSION_ACTION, newRootComment);
	}

	private void clearAdapterEntries() {
		getAdapter().getEntries().clear();
	}

	private CommentEntry _discussionComment;
	private ViewGroup _discussionCommentLayout;
	private TextView _userNameTextView;
	private TextView _bodyTextView;
	private UserPortraitScreenlet _userPortraitScreenlet;
	private TextView _createDateTextView;
	private TextView _editedTextView;

	private boolean _htmlBody;
	private ImageView _discussionSeparator;
}
