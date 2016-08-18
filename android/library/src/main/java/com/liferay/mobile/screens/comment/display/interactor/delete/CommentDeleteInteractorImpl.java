package com.liferay.mobile.screens.comment.display.interactor.delete;

import com.liferay.mobile.screens.base.thread.BaseCachedWriteThreadRemoteInteractor;
import com.liferay.mobile.screens.comment.display.CommentDisplayScreenlet;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.comment.display.interactor.CommentEvent;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteInteractorImpl
	extends BaseCachedWriteThreadRemoteInteractor<CommentDisplayInteractorListener, CommentEvent> {

	@Override
	public CommentEvent execute(CommentEvent event) throws Exception {

		long commentId = event.getCommentId();

		validate(commentId);

		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(getSession());

		service.deleteComment(commentId);

		return new CommentEvent();
	}

	@Override
	protected CommentEvent createEvent(Object[] args) throws Exception {
		long commentId = (long) args[0];
		return new CommentEvent(commentId, null, 0, null);
	}

	@Override
	public void onSuccess(CommentEvent event) throws Exception {
		getListener().onDeleteCommentSuccess();
	}

	@Override
	protected void onFailure(CommentEvent event) {
		getListener().error(event.getException(), CommentDisplayScreenlet.DELETE_COMMENT_ACTION);
	}

	protected void validate(long commentId) {
		if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
