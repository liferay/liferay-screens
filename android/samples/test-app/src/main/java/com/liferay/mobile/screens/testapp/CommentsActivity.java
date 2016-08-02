package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListListener;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
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

	@Override public void onDeleteCommentFailure(CommentEntry commentEntry, Exception e) {
		error(String.format("Error deleting comment with id: %d", commentEntry.getCommentId()), e);
	}

	@Override public void onDeleteCommentSuccess(CommentEntry commentEntry) {
		info(String.format("Comment with id: %d succesfully deleted", commentEntry.getCommentId()));
	}

	@Override public void onUpdateCommentFailure(CommentEntry commentEntry, Exception e) {
		error(String.format("Error updating comment with id: %d", commentEntry.getCommentId()), e);
	}

	@Override public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		info(String.format("Comment with id: %d succesfully updated", commentEntry.getCommentId()));
	}

	@Override public void onAddCommentFailure(String body, Exception e) {
		error("Error adding comment", e);
	}

	@Override public void onAddCommentSuccess(CommentEntry commentEntry) {
		info(String.format("Comment succesfully added, new id: %d", commentEntry.getCommentId()));
	}

	@Override public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {
		error(String.format("Error receiving page: %d", page), e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<CommentEntry> entries, int rowCount) {
	}

	@Override public void onListItemSelected(CommentEntry element, View view) {
		info(String.format("Selected comment with id: %d", element.getCommentId()));
	}

	@Override public void loadingFromCache(boolean success) {
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
	}

	@Override public void storingToCache(Object object) {
	}

	private CommentListScreenlet _screenlet;

}
