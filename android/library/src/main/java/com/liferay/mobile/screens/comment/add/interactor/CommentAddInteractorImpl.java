package com.liferay.mobile.screens.comment.add.interactor;

import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.screens.base.interactor.BaseRemoteInteractor;
import com.liferay.mobile.screens.comment.add.CommentAddListener;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.service.v70.CommentmanagerjsonwsService;

/**
 * @author Alejandro Hernández
 */
public class CommentAddInteractorImpl extends BaseRemoteInteractor<CommentAddListener> implements CommentAddInteractor {

	public CommentAddInteractorImpl(int targetScreenletId) {
		super(targetScreenletId);
	}

	@Override
	public void addComment(long groupId, String className, long classPK, String body) throws Exception {

		validate(groupId, className, classPK, body);

		Session session = SessionContext.createSessionFromCurrentSession();
		session.setCallback(new CommentAddCallback(body, getTargetScreenletId()));
		CommentmanagerjsonwsService service = new CommentmanagerjsonwsService(session);

		service.addComment(groupId, className, classPK, body);
	}

	public void onEvent(CommentAddEvent event) {
		if (!isValidEvent(event)) {
			return;
		}

		if (event.isFailed()) {
			getListener().onAddCommentFailure(event.getBody(), event.getException());
		} else {
			getListener().onAddCommentSuccess(event.getCommentEntry());
		}
	}

	protected void validate(long groupId, String className, long classPK, String body) {

		if (body.isEmpty()) {
			throw new IllegalArgumentException("comment body cannot be empty");
		} else if (groupId <= 0) {
			throw new IllegalArgumentException("groupId must be greater than 0");
		} else if (className.isEmpty()) {
			throw new IllegalArgumentException("className cannot be empty");
		} else if (classPK <= 0) {
			throw new IllegalArgumentException("classPK must be greater than 0");
		}
	}
}