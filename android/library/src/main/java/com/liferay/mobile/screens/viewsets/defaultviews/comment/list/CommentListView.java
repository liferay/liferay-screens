package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.models.CommentEntry;

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
		getAdapter().setHtmlBody(htmlBody);
	}

	@Override
	public void changeToCommentDiscussion(CommentEntry newRootComment) {
		_discussionComment = newRootComment;

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
}
