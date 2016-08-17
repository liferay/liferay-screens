package com.liferay.mobile.screens.comment.display.interactor.delete;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.thread.BaseRemoteInteractorNew;
import com.liferay.mobile.screens.base.thread.event.BasicThreadEvent;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteInteractorImpl
	extends BaseRemoteInteractorNew<CommentDisplayInteractorListener, BasicThreadEvent> {

	@Override
	public BasicThreadEvent execute(Object... args) throws Exception {

		long commentId = (long) args[0];

		validate(commentId);

		Session session = SessionContext.createSessionFromCurrentSession();
		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

		service.deleteComment(commentId);

		return new BasicThreadEvent();
	}

	@Override
	public void onSuccess(BasicThreadEvent event) throws Exception {
		getListener().onDeleteCommentSuccess();
	}

	@Override
	public void onFailure(Exception e) {
		getListener().onDeleteCommentFailure(e);
	}

	protected void validate(long commentId) {

		if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
