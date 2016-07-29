package com.liferay.mobile.screens.viewsets.defaultviews.comment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.screens.R;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.comment.CommentListScreenlet;
import com.liferay.mobile.screens.comment.view.CommentListViewModel;
import com.liferay.mobile.screens.comment.view.CommentView;
import com.liferay.mobile.screens.comment.view.CommentViewListener;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Alejandro Hernández
 */
public class CommentListView extends
	BaseListScreenletView<CommentEntry, CommentListAdapter.CommentViewHolder, CommentListAdapter>
	implements CommentListViewModel, CommentViewListener, View.OnClickListener {

	public CommentListView(Context context) {
		super(context);
	}

	public CommentListView(Context context, AttributeSet attributes) {
		super(context, attributes);
	}

	public CommentListView(Context context, AttributeSet attributes, int defaultStyle) {
		super(context, attributes, defaultStyle);
	}

	@Override public void setHtmlBody(boolean htmlBody) {
		getAdapter().setHtmlBody(htmlBody);
	}

	@Override
	public void showFinishOperation(int page, List<CommentEntry> serverEntries, int rowCount) {
		super.showFinishOperation(page, serverEntries, rowCount);

		_sendButton.setEnabled(true);

		if (getAdapter().getEntries().isEmpty()) {
			_emptyListTextView.setVisibility(VISIBLE);
		} else {
			_emptyListTextView.setVisibility(GONE);
		}
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		_emptyListTextView = (TextView) findViewById(R.id.comment_empty_list);
		_sendButton = (Button) findViewById(R.id.comment_send);
		_addCommentEditText = (EditText) findViewById(R.id.comment_add);
		_sendButton.setOnClickListener(this);

		setFocusableInTouchMode(true);
	}

	@Override protected int getItemLayoutId() {
		return R.layout.comment_row_default;
	}

	@Override
	protected CommentListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
		return new CommentListAdapter(itemLayoutId, itemProgressLayoutId, this);
	}

	private void clearAdapterEntries() {
		getAdapter().getEntries().clear();
	}

	@Override public void onEditButtonClicked(long commentId, String newBody) {
		clearAdapterEntries();
		getScreenlet().performUserAction(CommentListScreenlet.UPDATE_COMMENT_ACTION, commentId,
			newBody);
	}

	@Override public void onDeleteButtonClicked(long commentId) {
		clearAdapterEntries();
		getScreenlet().performUserAction(CommentListScreenlet.DELETE_COMMENT_ACTION, commentId);
	}

	@Override public void onClick(View v) {
		int i = v.getId();
		if (i == R.id.comment_send) {
			String body = _addCommentEditText.getText().toString();
			if (!body.isEmpty()) {
				clearAdapterEntries();
				_addCommentEditText.setText("");
				_sendButton.setEnabled(false);
				getScreenlet().performUserAction(CommentListScreenlet.ADD_COMMENT_ACTION, body);
			}
		}
	}

	private TextView _emptyListTextView;
	private Button _sendButton;
	private EditText _addCommentEditText;
}
