package com.liferay.mobile.screens.comment.add;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.add.view.CommentAddViewModel;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentAddScreenlet extends BaseScreenlet<CommentAddViewModel, Interactor>
	implements CommentAddListener {

	public CommentAddScreenlet(Context context) {
		super(context);
	}

	public CommentAddScreenlet(Context context, AttributeSet attrs) {
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

	@Override public void onAddCommentFailure(String body, Exception e) {

	}

	@Override public void onAddCommentSuccess(CommentEntry commentEntry) {

	}
}
