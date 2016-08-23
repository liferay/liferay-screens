package com.liferay.mobile.screens.comment.display.interactor.delete;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteInteractorImpl extends BaseRemoteInteractor<CommentDisplayInteractorListener>
	implements CommentDeleteInteractor {

	public CommentDeleteInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void deleteComment(long commentId) {
		try {
			validate(commentId);

			Session session = SessionContext.createSessionFromCurrentSession();

			session.setCallback(new CommentDeleteCallback(getTargetScreenletId()));

			CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

			service.deleteComment(commentId);
		} catch (Exception e) {
			getListener().onDeleteCommentFailure(e);
		}
	}

	public void onEvent(CommentDeleteEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDeleteCommentFailure(event.getException());
		} else {
			getListener().onDeleteCommentSuccess();
		}
	}

	protected void validate(long commentId) {

		if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		}
	}
}
