package com.liferay.mobile.screens.testapp;

import android.os.Bundle;

import com.liferay.mobile.screens.base.interactor.CustomInteractorListener;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.bookmark.AddBookmarkScreenlet;
import com.liferay.mobile.screens.bookmark.interactor.AddBookmarkInteractorImpl;
import com.liferay.mobile.screens.bookmark.interactor.AddBookmarkListener;

/**
 * @author Javier Gamarra
 */
public class AddBookmarkActivity extends ThemeActivity implements AddBookmarkListener, CustomInteractorListener {

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.add_bookmark);

		_screenlet = (AddBookmarkScreenlet) findViewById(R.id.bookmark_screenlet);
		_screenlet.setListener(this);

		_screenlet.setCustomInteractorListener(this);
	}

	@Override
	public void onAddBookmarkFailure(Exception exception) {
		error("Error adding bookmark", exception);
	}

	@Override
	public void onAddBookmarkSuccess() {
		info("Bookmark added!");
	}

	@Override
	public Interactor createInteractor(String actionName) {
		return new AddBookmarkInteractorImpl(_screenlet.getScreenletId());
	}

	private AddBookmarkScreenlet _screenlet;
}
