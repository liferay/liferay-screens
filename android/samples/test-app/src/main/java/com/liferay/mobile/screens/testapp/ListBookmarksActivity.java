package com.liferay.mobile.screens.testapp;

import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.listbookmark.Bookmark;
import com.liferay.mobile.screens.listbookmark.BookmarkListListener;
import com.liferay.mobile.screens.listbookmark.BookmarkListScreenlet;
import java.util.List;

public class ListBookmarksActivity extends ThemeActivity implements BookmarkListListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_bookmarks);

		BookmarkListScreenlet bookmarkListScreenlet = findViewById(R.id.bookmarklist_screenlet);
		bookmarkListScreenlet.setListener(this);
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List<Bookmark> entries, int rowCount) {
		info(rowCount + " " + getString(R.string.rows_received_info) + " " + startRow);
	}

	@Override
	public void onListItemSelected(Bookmark element, View view) {

	}

	@Override
	public void error(Exception e, String userAction) {

	}

	@Override
	public void interactorCalled() {

	}
}
