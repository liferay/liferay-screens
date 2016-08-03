package com.liferay.mobile.screens.viewsets.defaultviews.comment.list;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.display.CommentDisplayListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.comment.list.view.CommentListViewModel;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentListView extends
	BaseListScreenletView<CommentEntry, CommentListAdapter.CommentViewHolder, CommentListAdapter>
	implements CommentListViewModel, CommentDisplayListener {

	public CommentListView(Context context) {
		super(context);
	}

	public CommentListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public CommentListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override public void refreshView() {
		getAdapter().getEntries().clear();
	}

	@Override public void setEditable(boolean editable) {
		_editable = editable;
	}

	@Override
	public void showFinishOperation(int page, List<CommentEntry> serverEntries, int rowCount) {
		getAdapter().setGroupId(getCommentListScreenlet().getGroupId());
		getAdapter().setClassPK(getCommentListScreenlet().getClassPK());
		getAdapter().setClassName(getCommentListScreenlet().getClassName());
		getAdapter().setEditable(_editable);

		super.showFinishOperation(page, serverEntries, rowCount);

		if (getAdapter().getEntries().isEmpty()) {
			_emptyListTextView.setVisibility(VISIBLE);
		} else {
			_emptyListTextView.setVisibility(GONE);
		}
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		_emptyListTextView = (TextView) findViewById(R.id.comment_empty_list);

		setFocusableInTouchMode(true);
	}

	@Override protected int getItemLayoutId() {
		return R.layout.comment_row_default;
	}

	@Override
	protected CommentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new CommentListAdapter(itemLayoutId, itemProgressLayoutId, this, this);
	}

	private CommentListScreenlet getCommentListScreenlet() {
		return (CommentListScreenlet) getScreenlet();
	}

	@Override public void onDeleteCommentFailure(CommentEntry commentEntry, Exception e) {
		getCommentListScreenlet().onDeleteCommentFailure(commentEntry, e);
	}

	@Override public void onDeleteCommentSuccess(CommentEntry commentEntry) {
		refreshView();
		getCommentListScreenlet().onDeleteCommentSuccess(commentEntry);
	}

	@Override public void onUpdateCommentFailure(CommentEntry commentEntry, Exception e) {
		getCommentListScreenlet().onUpdateCommentFailure(commentEntry, e);
	}

	@Override public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		getCommentListScreenlet().onUpdateCommentSuccess(commentEntry);
	}

	@Override public void onLoadCommentFailure(long commentId, Exception e) {
	}

	@Override public void onLoadCommentSuccess(CommentEntry commentEntry) {
	}

	@Override public void loadingFromCache(boolean success) {
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
	}

	@Override public void storingToCache(Object object) {
	}

	private TextView _emptyListTextView;
	private boolean _editable;
}
