package com.liferay.mobile.screens.comment.display.interactor.update;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.comment.display.interactor.CommentDisplayInteractorListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentUpdateInteractorImpl extends BaseRemoteInteractor<CommentDisplayInteractorListener>
	implements CommentUpdateInteractor {

	public CommentUpdateInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void updateComment(long groupId, String className, long classPK, long commentId, String newBody)
		throws Exception {

		validate(groupId, className, classPK, commentId, newBody);

		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new CommentUpdateCallback(getTargetScreenletId()));
		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

		service.updateComment(groupId, className, classPK, commentId, newBody);
	}

	public void onEvent(CommentUpdateEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onUpdateCommentFailure(event.getException());
		} else {
			getListener().onUpdateCommentSuccess(event.getCommentEntry());
		}
	}

	protected void validate(long groupId, String className, long classPK, long commentId, String newBody) {

		if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		} else if (commentId <= 0) {
			throw new IllegalArgumentException("commentId cannot be 0 or negative");
		} else if (newBody.isEmpty()) {
			throw new IllegalArgumentException("new body for comment cannot be empty");
		}
	}
}
