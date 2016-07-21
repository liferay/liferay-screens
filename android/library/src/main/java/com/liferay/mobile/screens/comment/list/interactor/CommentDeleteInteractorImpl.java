package com.liferay.mobile.screens.comment.list.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentDeleteInteractorImpl extends BaseRemoteInteractor<CommentListInteractorListener>
	implements CommentDeleteInteractor {

	public CommentDeleteInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(CommentDeleteEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onDeleteCommentFailure(_commentId, event.getException());
		}
		else {
			getListener().onDeleteCommentSuccess(_commentId);
		}
	}

	@Override
	public void deleteComment(long commentId)
		throws Exception {

		validate(commentId);

		_commentId = commentId;

		CommentmanagerjsonwsService service = getCommentsService();

		service.deleteComment(commentId);
	}

	protected CommentmanagerjsonwsService getCommentsService() {

		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new CommentDeleteCallback(getTargetScreenletId()));

		return new CommentmanagerjsonwsService(session);
	}

	protected void validate(long commentId) {

		if (commentId <= 0) {
			throw new IllegalArgumentException(
				"commentId cannot be 0 or negative");
		}
	}

	private long _commentId;
}
