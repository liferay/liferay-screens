package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.comment.CommentListListener;
import com.liferay.mobile.screens.comment.CommentListScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentsActivity extends ThemeActivity implements CommentListListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.comment_list);

		_screenlet = (CommentListScreenlet) findViewById(R.id.comment_list_screenlet);
		_screenlet.setGroupId(LiferayServerContext.getGroupId());
		_screenlet.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		_screenlet.loadPage(0);
	}

	@Override public void onDeleteCommentFailure(long commentId, Exception e) {
		error(String.format("Error deleting comment with id: %d%n", commentId), e);
	}

	@Override public void onDeleteCommentSuccess(long commentId) {
		info(String.format("Comment with id: %d%n succesfully deleted", commentId));
	}

	@Override public void onUpdateCommentFailure(long commentId, Exception e) {
		error(String.format("Error updating comment with id: %d%n", commentId), e);
	}

	@Override public void onUpdateCommentSuccess(long commentId) {
		info(String.format("Comment with id: %d%n succesfully updated", commentId));
	}

	@Override public void onAddCommentFailure(String body, Exception e) {
		error("Error adding comment", e);
	}

	@Override public void onAddCommentSuccess(CommentEntry commentEntry) {
		//info(String.format("Comment succesfully added, new id: %d%n", commentEntry.getCommentId()));
	}

	@Override public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {
		error(String.format("Error receiving page: %d", page), e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<CommentEntry> entries, int rowCount) {
	}

	@Override public void onListItemSelected(CommentEntry element, View view) {
	}

	@Override public void loadingFromCache(boolean success) {
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
	}

	@Override public void storingToCache(Object object) {
	}

	private CommentListScreenlet _screenlet;

}
