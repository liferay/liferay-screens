package com.liferay.mobile.screens.testapp;

import android.os.Bundle;

import com.liferay.mobile.screens.bookmark.AddBookmarkScreenlet;
import com.liferay.mobile.screens.bookmark.interactor.AddBookmarkListener;
import com.liferay.mobile.screens.viewsets.defaultviews.LiferayCrouton;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkActivity extends ThemeActivity implements AddBookmarkListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.add_bookmark);

		AddBookmarkScreenlet screenlet = (AddBookmarkScreenlet) findViewById(R.id.bookmark_screenlet);
		screenlet.setListener(this);
	}

	@Override
	public void onAddBookmarkFailure(Exception exception) {

	}

	@Override
	public void onAddBookmarkSuccess() {
		LiferayCrouton.info(this, "Bookmark added!");
	}
}
