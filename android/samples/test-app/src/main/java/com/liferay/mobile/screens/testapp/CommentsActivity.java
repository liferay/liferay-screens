package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.base.list.BaseListScreenlet;
import com.liferay.mobile.screens.comment.list.CommentListScreenlet;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.models.CommentEntry;
import java.util.List;

/**
 * @author Alejandro Hernández
 */
public class CommentsActivity extends ThemeActivity implements BaseListListener<CommentEntry> {

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

	@Override public void onListPageFailed(BaseListScreenlet source, int page, Exception e) {
		error("There was an error retrieving page #" + page, e);
	}

	@Override
	public void onListPageReceived(BaseListScreenlet source, int page, List<CommentEntry> entries, int rowCount) {
		info("Page #" + page + " retrieved succesfully");
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
