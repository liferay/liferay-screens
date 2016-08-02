package com.liferay.mobile.screens.comment.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayScreenlet extends BaseScreenlet<CommentDisplayViewModel, Interactor>
	implements CommentDisplayInteractorListener {

	public CommentDisplayScreenlet(Context context) {
		super(context);
	}

	public CommentDisplayScreenlet(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override protected View createScreenletView(Context context, AttributeSet attributes) {
		return null;
	}

	@Override protected Interactor createInteractor(String actionName) {
		return null;
	}

	@Override
	protected void onUserAction(String userActionName, Interactor interactor, Object... args) {

	}

	@Override public void onLoadCommentFailure(Exception e) {

	}

	@Override public void onLoadCommentSuccess(CommentEntry commentEntry) {

	}

	@Override public void onDeleteCommentFailure(Exception e) {

	}

	@Override public void onDeleteCommentSuccess() {

	}

	@Override public void onUpdateCommentFailure(Exception e) {

	}

	@Override public void onUpdateCommentSuccess(CommentEntry commentEntry) {

	}

	@Override public void loadingFromCache(boolean success) {

	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {

	}

	@Override public void storingToCache(Object object) {

	}
}
