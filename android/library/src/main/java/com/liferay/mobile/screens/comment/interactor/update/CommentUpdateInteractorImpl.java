package com.liferay.mobile.screens.comment.interactor.update;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v7.commentmanagerjsonws.CommentmanagerjsonwsService;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.comment.interactor.CommentListInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateInteractorImpl extends BaseRemoteInteractor<CommentListInteractorListener>
	implements CommentUpdateInteractor {

	public CommentUpdateInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	public void onEvent(CommentUpdateEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onUpdateCommentFailure(_commentId, event.getException());
		} else {
			getListener().onUpdateCommentSuccess(_commentId);
		}
	}

	@Override
	public void updateComment(String className, long classPK, long commentId, String newBody)
		throws Exception {

		validate(commentId, newBody);

		_commentId = commentId;

		CommentmanagerjsonwsService service = getCommentsService();

		service.updateComment(className, classPK, commentId, "", newBody);
	}

	protected CommentmanagerjsonwsService getCommentsService() {

		Session session = SessionContext.createSessionFromCurrentSession();

		session.setCallback(new CommentUpdateCallback(getTargetScreenletId()));

		return new CommentmanagerjsonwsService(session);
	}

	protected void validate(long commentId, String newBody) {

		if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		} else if (newBody.isEmpty()) {
			throw new IllegalArgumentException("new body for comment cannot be empty");
		}
	}

	private long _commentId;
}
