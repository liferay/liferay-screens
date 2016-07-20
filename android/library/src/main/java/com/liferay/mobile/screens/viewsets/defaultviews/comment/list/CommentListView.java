package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.comment.list.view.CommentView;
import com.liferay.mobile.screens.comment.list.view.CommentViewListener;
import com.liferay.mobile.screens.models.CommentEntry;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentListView
	extends BaseListScreenletView<CommentEntry, CommentListAdapter.CommentViewHolder, CommentListAdapter>
	implements CommentListViewModel, CommentViewListener {

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
		_commentView = (CommentView) findViewById(R.id.discussion_comment);
		_discussionSeparator = (ImageView) findViewById(R.id.comment_separator);
		_emptyListTextView = (TextView) findViewById(R.id.comment_empty_list);

		_commentView.setListener(this);
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && _discussionComment != null) {
			clearAdapterEntries();
			_emptyListTextView.setVisibility(GONE);
			getScreenlet().performUserAction(BaseScreenlet.DEFAULT_ACTION, CommentListScreenlet.OUT_DISCUSSION_ACTION);
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	@Override protected CommentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new CommentListAdapter(itemLayoutId, itemProgressLayoutId, this, getContext());
	}

	@Override public void showFinishOperation(int page, List<CommentEntry> serverEntries, int rowCount) {
		super.showFinishOperation(page, serverEntries, rowCount);

		if (getAdapter().getEntries().isEmpty()) {
			_emptyListTextView.setText(_discussionComment != null ? R.string.empty_replies : R.string.empty_comments);
			_emptyListTextView.setVisibility(VISIBLE);
		} else {
			_emptyListTextView.setVisibility(GONE);
		}
	}

	@Override protected int getItemLayoutId() {
		return R.layout.comment_row_default;
	}

	@Override public void setHtmlBody(boolean htmlBody) {
		getAdapter().setHtmlBody(htmlBody);
		_commentView.setHtmlBody(htmlBody);
	}

	@Override
	public void changeToCommentDiscussion(CommentEntry newRootComment) {
		_discussionComment = newRootComment;

		if (newRootComment == null) {
			_commentView.setVisibility(GONE);
			_discussionSeparator.setVisibility(GONE);
		} else {
			_commentView.setCommentEntry(newRootComment);
			_commentView.reloadUserPortrait();
			_commentView.hideRepliesCounter();
			_commentView.setVisibility(VISIBLE);
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

	@Override public void onEditButtonClicked(long commentId, String newBody) {
	}

	@Override public void onDeleteButtonClicked(long commentId) {
	}

	private CommentEntry _discussionComment;
	private CommentView _commentView;
	private ImageView _discussionSeparator;
	private TextView _emptyListTextView;
}
