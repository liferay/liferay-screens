package com.liferay.mobile.screens.comment.display;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.base.interactor.Interactor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.delete.CommentDeleteInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.load.CommentLoadInteractorImpl;
import com.liferay.mobile.screens.comment.display.interactor.update.CommentUpdateInteractorImpl;
import com.liferay.mobile.screens.comment.display.view.CommentDisplayViewModel;
import com.liferay.mobile.screens.models.CommentEntry;

/**
 * @author Alejandro Hernández
 */
public class CommentDisplayScreenlet extends BaseScreenlet<CommentDisplayViewModel, Interactor>
	implements CommentDisplayInteractorListener {

	public static final String DELETE_COMMENT_ACTION = "delete_comment";
	public static final String UPDATE_COMMENT_ACTION = "update_comment";

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
		switch (actionName) {
			case DELETE_COMMENT_ACTION:
				return new CommentDeleteInteractorImpl(getScreenletId());
			case UPDATE_COMMENT_ACTION:
				return new CommentUpdateInteractorImpl(getScreenletId());
			default:
				return new CommentLoadInteractorImpl(getScreenletId());
		}
	}

	@Override
	protected void onUserAction(String userActionName, Interactor interactor, Object... args) {

	}

	@Override public void onLoadCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onLoadCommentFailure(_commentId, e);
		}
	}

	@Override public void onLoadCommentSuccess(CommentEntry commentEntry) {
		if (getListener() != null) {
			getListener().onLoadCommentSuccess(commentEntry);
		}

		setCommentEntry(commentEntry);
	}

	@Override public void onDeleteCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onDeleteCommentFailure(_commentEntry, e);
		}
	}

	@Override public void onDeleteCommentSuccess() {
		if (getListener() != null) {
			getListener().onDeleteCommentSuccess(_commentEntry);
		}
	}

	@Override public void onUpdateCommentFailure(Exception e) {
		if (getListener() != null) {
			getListener().onUpdateCommentFailure(_commentEntry, e);
		}
	}

	@Override public void onUpdateCommentSuccess(CommentEntry commentEntry) {
		if (getListener() != null) {
			getListener().onUpdateCommentSuccess(commentEntry);
		}

		setCommentEntry(commentEntry);
	}

	@Override public void loadingFromCache(boolean success) {
		if (getListener() != null) {
			getListener().loadingFromCache(success);
		}
	}

	@Override public void retrievingOnline(boolean triedInCache, Exception e) {
		if (getListener() != null) {
			getListener().retrievingOnline(triedInCache, e);
		}
	}

	@Override public void storingToCache(Object object) {
		if (getListener() != null) {
			getListener().storingToCache(object);
		}
	}

	public CommentDisplayListener getListener() {
		return _listener;
	}

	public void setListener(CommentDisplayListener listener) {
		_listener = listener;
	}

	public CommentEntry getCommentEntry() {
		return _commentEntry;
	}

	public void setCommentEntry(CommentEntry commentEntry) {
		_commentEntry = commentEntry;
		getViewModel().showFinishOperation(_commentEntry);
	}

	public long getCommentId() {
		return _commentId;
	}

	public void setCommentId(long commentId) {
		_commentId = commentId;
	}

	private CommentDisplayListener _listener;

	private CommentEntry _commentEntry;
	private long _commentId;
}
